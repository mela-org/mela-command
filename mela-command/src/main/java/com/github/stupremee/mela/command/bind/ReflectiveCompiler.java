package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ReflectiveCompiler implements CommandCompiler {

  private final CommandBindings bindings;

  @Inject
  public ReflectiveCompiler(CommandBindings bindings) {
    this.bindings = bindings;
  }

  @Nonnull
  @Override
  public Set<? extends CommandCallable> compile(@Nonnull Object command) {
    return Arrays.stream(command.getClass().getMethods())
        .filter((method) -> method.isAnnotationPresent(Command.class))
        .map((method) -> ReflectiveCallable.from(command, method, bindings))
        .collect(Collectors.toUnmodifiableSet());
  }
}
