package com.github.stupremee.mela.command;

import com.google.inject.ImplementedBy;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// @ImplementedBy
public interface Dispatcher {

  boolean call(String command, CommandContext context);

  CommandStash getCommandStash();

}
