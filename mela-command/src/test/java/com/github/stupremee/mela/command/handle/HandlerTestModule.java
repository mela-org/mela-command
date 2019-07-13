package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.TestException;
import com.github.stupremee.mela.command.TestModule;
import com.github.stupremee.mela.command.bind.CommandBinder;
import com.google.inject.Binder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class HandlerTestModule extends TestModule {

  private final TestExceptionHandler handler;

  HandlerTestModule() {
    super(new HandlerTestCommand());
    handler = new TestExceptionHandler();
  }

  @Override
  protected void configureCommandBindings(CommandBinder binder) {
    binder.root().handle(TestException.class).with(handler);
  }

  @Override
  protected void configureNormalBindings(Binder binder) {
    binder.bind(TestExceptionHandler.class).toInstance(handler);
  }
}
