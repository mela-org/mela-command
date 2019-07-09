package com.github.stupremee.mela.command.util;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandGroup;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandInput {

  private final CommandGroup group;
  private final CommandCallable callable;
  private final String arguments;

  CommandInput(@Nonnull CommandGroup group, @Nullable CommandCallable callable, @Nonnull String arguments) {
    this.group = group;
    this.callable = callable;
    this.arguments = arguments;
  }

  @Nonnull
  public CommandGroup getGroup() {
    return group;
  }

  @Nonnull
  public Optional<CommandCallable> getCallable() {
    return Optional.ofNullable(callable);
  }

  @Nonnull
  public String getArguments() {
    return arguments;
  }
}
