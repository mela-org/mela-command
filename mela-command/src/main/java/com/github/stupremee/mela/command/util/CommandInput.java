package com.github.stupremee.mela.command.util;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.bind.tree.CommandGroup;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandInput {

  private final CommandGroup group;
  private final CommandCallable callable;
  private final String arguments;

  CommandInput(@Nonnull CommandGroup group, @Nullable CommandCallable callable, @Nonnull String arguments) {
    this.group = checkNotNull(group);
    this.callable = callable;
    this.arguments = checkNotNull(arguments);
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
