package io.github.mela.command.bind.parameter;

import io.github.mela.command.bind.map.ArgumentChain;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Parameters {

  private final List<CommandParameter> parameters;

  private Parameters(List<CommandParameter> parameters) {
    this.parameters = List.copyOf(parameters);
  }

  @Nonnull
  public Object[] map(@Nonnull ArgumentChain chain, @Nonnull CommandContext context) throws Throwable {
    List<Object> mappedArgs = new ArrayList<>();
    for (CommandParameter parameter : parameters) {
      mappedArgs.add(parameter.advance(chain.subChain(), context));
    }

    if (parameters.size() != mappedArgs.size()) {
      throw new RuntimeException("Invalid amount of arguments; expected: "
          + parameters.size() + ", got: " + mappedArgs.size()); // TODO: 11.09.2019
    }
    return mappedArgs.toArray();
  }

  @Nonnull
  public static Parameters from(@Nonnull Method method, @Nonnull CommandBindings bindings) {
    checkNotNull(method);
    checkNotNull(bindings);
    List<CommandParameter> parameters = Arrays.stream(method.getParameters())
        .map((parameter) -> CommandParameter.from(parameter, bindings))
        .collect(Collectors.toList());
    return new Parameters(parameters);
  }
}
