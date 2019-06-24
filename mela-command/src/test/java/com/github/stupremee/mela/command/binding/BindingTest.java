package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.inject.AbstractModule;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 24.06.2019 Tests: Simple bindings (interceptor, handler, parameter, commands), multi-module bindings
public class BindingTest {

  private static final Object command = new Object();
  private static final ArgumentMapper<Object> mapper = (o, c) -> null;
  private static final ArgumentMapper<Object> mapperOverride = (o, c) -> null;
  private static final Interceptor<TestAnnotation> interceptor = (c) -> true;
  private static final Interceptor<TestAnnotation> interceptorOverride = (c) -> true;
  private static final ExceptionHandler<TestException> handler = (t, c) -> {};
  private static final ExceptionHandler<TestException> handlerOverride = (t, c) -> {};

}
