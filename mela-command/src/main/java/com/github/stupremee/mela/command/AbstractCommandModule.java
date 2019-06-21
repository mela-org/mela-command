package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.binder.CommandBinder;
import com.google.inject.AbstractModule;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class AbstractCommandModule extends AbstractModule {

  protected CommandBinder commands() {
    return null;
  }

}
