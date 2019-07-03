package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.TestException;
import com.github.stupremee.mela.command.TestModule;
import com.github.stupremee.mela.command.bind.CommandBinder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class HandlerTestModule extends TestModule {

  private final ExceptionHandler<TestException> handler;

  protected HandlerTestModule(ExceptionHandler<TestException> handler) {
    super(new HandlerTestCommand());
    this.handler = handler;
  }

  @Override
  protected void configureCommandBindings(CommandBinder binder) {
    binder.root().handle(TestException.class).with(handler);
  }
}
