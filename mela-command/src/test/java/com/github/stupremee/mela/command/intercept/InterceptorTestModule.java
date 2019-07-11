package com.github.stupremee.mela.command.intercept;

import com.github.stupremee.mela.command.TestAnnotation;
import com.github.stupremee.mela.command.TestModule;
import com.google.inject.Binder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InterceptorTestModule extends TestModule {

  InterceptorTestModule() {
    super(new InterceptorTestCommand());
  }

  @Override
  protected void configureCommandBindings(CommandBinder binder) {
    binder.root().interceptAt(TestAnnotation.class).with((context) -> false);
  }

  @Override
  protected void configureNormalBindings(Binder binder) {
    binder.bind(InterceptorTestCommand.class).toInstance((InterceptorTestCommand) getRootCommand());
  }
}
