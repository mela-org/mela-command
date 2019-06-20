package com.github.stupremee.mela.command;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// @ImplementedBy
public interface CommandStash {

  CommandCallable select(String command);

  void register(CommandCallable commandCallable);

  void unregister(CommandCallable commandCallable);

}
