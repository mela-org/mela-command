package com.github.stupremee.mela.command.binder;

import java.lang.annotation.Annotation;

public interface CommandBinder {

  <T> ParameterBindingBuilder<T> bindParameter(Class<T> parameterType);

  <T extends Annotation> InterceptorBindingBuilder<T> interceptAt(Class<T> annotationType);

  <T extends Throwable> ExceptionBindingBuilder<T> handle(Class<T> exceptionType);

}
