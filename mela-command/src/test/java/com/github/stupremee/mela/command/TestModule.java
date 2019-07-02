package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.bind.CommandBinder;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class TestModule implements Module {

  @Override
  public final void configure(Binder binder) {
    configureCommandBindings(CommandBinder.create(binder));
  }

  protected abstract void configureCommandBindings(CommandBinder binder);
}
