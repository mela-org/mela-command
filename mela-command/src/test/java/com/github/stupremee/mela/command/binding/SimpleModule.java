package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
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
