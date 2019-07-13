package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.TestAnnotation;
import com.github.stupremee.mela.command.TestException;
import com.github.stupremee.mela.command.TestModule;
import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.github.stupremee.mela.command.compile.IdentityCompiler;
import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Binder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class BasicBindingTestModule extends TestModule {

  static final Object COMMAND = new NoOpCommand() {};

  private static final ArgumentMapper<Object> MAPPER = (o, c) -> null;
  private static final Interceptor<TestAnnotation> INTERCEPTOR = (c) -> true;
  private static final ExceptionHandler<TestException> HANDLER = (t, c) -> {};

  BasicBindingTestModule() {
    super(COMMAND);
  }

  @Override
  protected void configureCommandBindings(CommandBinder binder) {
    binder.root()
        .bindParameter(Object.class).toMapper(MAPPER)
        .interceptAt(TestAnnotation.class).with(INTERCEPTOR)
        .handle(TestException.class).with(HANDLER);
  }

  @Override
  protected void configureNormalBindings(Binder binder) {
    binder.bind(CommandCompiler.class).to(IdentityCompiler.class);
  }
}
