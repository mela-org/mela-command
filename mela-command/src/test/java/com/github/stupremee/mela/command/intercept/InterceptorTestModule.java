package com.github.stupremee.mela.command.intercept;

import com.github.stupremee.mela.command.TestAnnotation;
import com.github.stupremee.mela.command.TestModule;
import com.github.stupremee.mela.command.bind.CommandBinder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class InterceptorTestModule extends TestModule {

  public InterceptorTestModule() {
    super(command);
  }

  @Override
  protected void configureCommandBindings(CommandBinder binder) {
    binder.root().interceptAt(TestAnnotation.class)
  }

}
