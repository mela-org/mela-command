package com.github.stupremee.mela.command;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class DefaultDispatcher implements Dispatcher {

  @Override
  public void registerCommands(Object module) {

  }

  @Override
  public boolean call(String command, CommandContext context) {
    return false;
  }
}
