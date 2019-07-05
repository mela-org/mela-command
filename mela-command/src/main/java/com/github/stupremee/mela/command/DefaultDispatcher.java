package com.github.stupremee.mela.command;

import com.google.inject.Inject;

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
  public boolean dispatch(String command, CommandContext context) {
    return false;
  }
}
