package io.github.mela.command.bind.parameter;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.core.ArgumentException;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Parameters {

  private final Map<CommandParameter, MappingProcessor> parameters;

  private Parameters(Map<CommandParameter, MappingProcessor> parameters) {
    this.parameters = Map.copyOf(parameters);
  }

  @Nonnull
  public Object[] map(@Nonnull Arguments arguments, @Nonnull ContextMap context) throws Throwable {
    String original = arguments.toString();
    List<Object> mappedArgs = new ArrayList<>();
    for (Map.Entry<CommandParameter, MappingProcessor> entry : parameters.entrySet()) {
      CommandParameter parameter = entry.getKey();
      MappingProcessor processor = entry.getValue();
      try {
        Object argument = processor.process(arguments, context);
        mappedArgs.add(argument);
      } catch (Throwable throwable) {
        if (throwable instanceof MappingProcessException) {
          ((MappingProcessException) throwable).setParameter(parameter);
        }
        throw throwable;
      }
    }

    if (arguments.hasNext()) {
      throw new ArgumentException("Invalid amount of arguments; some arguments were not processed.\n" +
          "(original arguments: \"" + original + "\"\narguments left: \"" + arguments + "\")");
    }
    return mappedArgs.toArray();
  }

  @Nonnull
  public static Parameters from(@Nonnull Method method, @Nonnull CommandBindings bindings) {
    checkNotNull(method);
    checkNotNull(bindings);
    Map<CommandParameter, MappingProcessor> parameters = new LinkedHashMap<>();
    for (Parameter parameter : method.getParameters()) {
      CommandParameter commandParameter = new CommandParameter(parameter);
      MappingProcessor processor = MappingProcessor.fromParameter(bindings, parameter);
      parameters.put(commandParameter, processor);
    }
    return new Parameters(parameters);
  }
}
