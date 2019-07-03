package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.bind.CommandBinder;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class TestModule implements Module {

  private final Object rootCommand;

  protected TestModule(Object rootCommand) {
    this.rootCommand = rootCommand;
  }

  @Override
  public final void configure(Binder binder) {
    CommandBinder commandBinder = CommandBinder.create(binder);
    if (rootCommand != null)
      commandBinder.root().add(rootCommand);
    configureCommandBindings(commandBinder);
  }

  protected abstract void configureCommandBindings(CommandBinder binder);
}
