package com.github.stupremee.mela.command;

import com.google.inject.ImplementedBy;
import com.google.inject.Singleton;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@ImplementedBy(DefaultDispatcher.class)
@Singleton
public interface Dispatcher {

  void registerCommands(Object module);

  boolean call(String command, CommandContext context);

}
