package com.github.stupremee.mela.command;

import com.google.inject.Inject;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultDispatcher implements Dispatcher {

  private final CommandGroup root;

  @Inject
  public DefaultDispatcher(CommandGroup root) {
    this.root = root;
  }

  @Override
  public boolean dispatch(@Nonnull String command, @Nonnull CommandContext context) {
    // TODO: 06.07.2019
    return false;
  }
}
