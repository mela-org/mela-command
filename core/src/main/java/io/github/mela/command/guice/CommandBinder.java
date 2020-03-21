package io.github.mela.command.guice;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.inject.Binder;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandBinder {

  private final CommandBindingNode root;

  private CommandBinder(Binder binder) {
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
