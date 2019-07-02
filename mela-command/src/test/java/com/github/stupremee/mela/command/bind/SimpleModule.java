package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.AbstractModule;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class SimpleModule extends AbstractModule {

  public static final Object COMMAND = new Object();
  public static final ArgumentMapper<Object> MAPPER = (o, c) -> null;
  public static final Interceptor<TestAnnotation> INTERCEPTOR = (c) -> true;
  public static final ExceptionHandler<TestException> HANDLER = (t, c) -> {};

  @Override
  protected void configure() {
    CommandBinder.create(binder()).parentNode()
        .add(COMMAND)
        .bindParameter(Object.class).toMapper(MAPPER)
        .interceptAt(TestAnnotation.class).with(INTERCEPTOR)
        .handle(TestException.class).with(HANDLER);
  }
}
