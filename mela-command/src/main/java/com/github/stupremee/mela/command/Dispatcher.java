package com.github.stupremee.mela.command;

import com.google.inject.ImplementedBy;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@ImplementedBy(DefaultDispatcher.class)
public interface Dispatcher {

  void registerCommands(Object module);

  boolean call(String command, CommandContext context);

}
