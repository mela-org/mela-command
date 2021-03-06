package io.github.mela.command.bind.parameter;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.core.ArgumentException;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.AbstractList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Parameters extends AbstractList<CommandParameter> {

  private final List<CommandParameter> commandParameters;
  private final Map<CommandParameter, MappingProcessor> parameters;

  private Parameters(Map<CommandParameter, MappingProcessor> parameters) {
    this.parameters = parameters;
    this.commandParameters = ImmutableList.copyOf(parameters.keySet());
  }

  @Nonnull
  public static Parameters from(@Nonnull Method method, @Nonnull CommandBindings bindings) {
    checkNotNull(method);
    checkNotNull(bindings);
    Map<CommandParameter, MappingProcessor> parameters = Maps.newLinkedHashMap();
    for (Parameter parameter : method.getParameters()) {
      CommandParameter commandParameter = CommandParameter.of(parameter);
      MappingProcessor processor = MappingProcessor.fromParameter(bindings, commandParameter);
      parameters.put(commandParameter, processor);
    }
    return new Parameters(parameters);
  }

  @Nonnull
  public Object[] map(@Nonnull CommandArguments arguments, @Nonnull CommandContext context)
      throws Throwable {
    List<Object> mappedArgs = Lists.newArrayList();
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
      throw ArgumentException.create(
          "Invalid amount of arguments; Some arguments were not processed.", arguments);
    }
    return mappedArgs.toArray();
  }

  @Override
  public CommandParameter get(int index) {
    return commandParameters.get(index);
  }

  @Override
  public int size() {
    return commandParameters.size();
  }
}
