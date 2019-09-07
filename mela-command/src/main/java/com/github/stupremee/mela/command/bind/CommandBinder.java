package com.github.stupremee.mela.command.bind;

import com.google.inject.Binder;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandBinder {

  private final CommandBindingNode root;

  private CommandBinder(@Nonnull Binder binder) {
    this.root = new CommandBindingNode(checkNotNull(binder));
  }

  @Nonnull
  public static CommandBinder create(@Nonnull Binder binder) {
    return new CommandBinder(binder);
  }

  @Nonnull
  public CommandBindingNode root() {
    return root;
  }

}
