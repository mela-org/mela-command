package io.github.mela.command.bind.parameter;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.Arguments;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.bind.map.ParameterMappingException;
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
  public Object[] map(@Nonnull Arguments arguments, @Nonnull ContextMap context) {
    List<Object> mappedArgs = new ArrayList<>();

    parameters.forEach((parameter, processor) -> {
      try {
        mappedArgs.add(processor.process(arguments.subChain(), context));
      } catch (Throwable throwable) {
        throw new ParameterMappingException("Failed to map argument for " + parameter
            + "(arguments: \"" + arguments + "\")", parameter);
      }
    });

    if (parameters.size() != mappedArgs.size()) {
      throw new MappingProcessException("Invalid amount of arguments; expected: "
          + parameters.size() + ", got: " + mappedArgs.size() + "(arguments: \"" + arguments + "\")");
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
