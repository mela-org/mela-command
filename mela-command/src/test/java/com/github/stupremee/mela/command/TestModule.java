package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.guice.CommandBinder;
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
    configureNormalBindings(binder);
    configureCommandBindings(commandBinder);
  }

  protected void configureNormalBindings(Binder binder) {}

  protected void configureCommandBindings(CommandBinder binder) {}

  protected Object getRootCommand() {
    return rootCommand;
  }
}
