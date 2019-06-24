package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.inject.AbstractModule;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class OverrideModule extends AbstractModule {

  public static final Object ADDITIONAL_COMMAND = new Object();
  public static final ArgumentMapper<Object> MAPPER_OVERRIDE = (o, c) -> null;
  public static final Interceptor<TestAnnotation> INTERCEPTOR_OVERRIDE = (c) -> true;
  public static final ExceptionHandler<TestException> HANDLER_OVERRIDE = (t, c) -> {};

  @Override
  protected void configure() {
    CommandBinder first = CommandBinder.create(binder());
    first.parentNode()
        .add(ADDITIONAL_COMMAND)
        .group("override")
            .bindParameter(Object.class).toMapper(MAPPER_OVERRIDE)
            .interceptAt(TestAnnotation.class).with(INTERCEPTOR_OVERRIDE)
            .handle(TestException.class).with(HANDLER_OVERRIDE);
  }
}
