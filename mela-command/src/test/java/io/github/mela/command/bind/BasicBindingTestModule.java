package io.github.mela.command.bind;

import io.github.mela.command.TestAnnotation;
import io.github.mela.command.TestException;
import io.github.mela.command.TestModule;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.compile.CommandCompiler;
import io.github.mela.command.compile.IdentityCompiler;
import io.github.mela.command.guice.CommandBinder;
import com.google.inject.Binder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class BasicBindingTestModule extends TestModule {

  static final Object COMMAND = new NoOpCommand() {};

  private static final ArgumentMapper<Object> MAPPER = (o, c) -> null;
  private static final CommandInterceptor<TestAnnotation> INTERCEPTOR = (c) -> true;
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
