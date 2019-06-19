package com.github.stupremee.mela.command;

import com.google.inject.Singleton;

import java.util.Collections;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class DefaultDispatcher implements Dispatcher {

  @Override
  public void registerCommands(Object module) {

  }

  @Override
  public boolean call(String command, CommandContext context) {
    return false;
  }

  @Override
  public Set<CommandCallable> getCommands() {
    return Collections.emptySet();
  }
}
