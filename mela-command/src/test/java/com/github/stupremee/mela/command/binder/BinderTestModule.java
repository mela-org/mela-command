package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.AbstractCommandModule;
import com.github.stupremee.mela.command.handler.ExceptionHandler;
import com.github.stupremee.mela.command.interceptor.Interceptor;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class BinderTestModule extends AbstractCommandModule {

  public static final String PARAMETER = "TEST";
  public static final String ANNOTATED_PARAMETER = "ANNOTATED_TEST";
  public static final Interceptor<TestAnnotation> INTERCEPTOR = new Interceptor<>() {};
  public static final ExceptionHandler<TestException> EXCEPTION_HANDLER = new ExceptionHandler<>() {};
  public static final ExceptionHandler<Exception> IGNORING_EXCEPTION_HANDLER = new ExceptionHandler<>() {};

  @Override
  protected void configure() {
    commands().bindParameter(String.class).toInstance(PARAMETER);
    commands().bindParameter(String.class).annotatedWith(TestAnnotation.class).toInstance(ANNOTATED_PARAMETER);
    commands().interceptAt(TestAnnotation.class).with(INTERCEPTOR);
    commands().handle(TestException.class).with(EXCEPTION_HANDLER);
    commands().handle(Exception.class).with(IGNORING_EXCEPTION_HANDLER);
  }

}
