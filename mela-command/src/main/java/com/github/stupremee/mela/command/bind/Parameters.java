package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.parameter.CommandParameter;
import com.github.stupremee.mela.command.bind.process.ArgumentChain;
import com.github.stupremee.mela.command.parse.Arguments;

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
  public Object[] map(@Nonnull Arguments arguments, @Nonnull CommandContext context) throws Throwable {
    List<Object> mappedArgs = new ArrayList<>();
    ArgumentChain chain = new ArgumentChain(arguments);
    for (CommandParameter parameter : parameters) {
      mappedArgs.add(parameter.advance(chain, context));
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
    if (method.getTypeParameters().length > 0) {
      throw new RuntimeException("Illegal method declaration: command methods must not have generic type parameters " +
          "(method: " + method + ")");
    }

    List<CommandParameter> parameters = Arrays.stream(method.getParameters())
        .map((parameter) -> CommandParameter.from(parameter, bindings))
        .collect(Collectors.toList());
    return new Parameters(parameters);
  }
}
