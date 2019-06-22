package com.github.stupremee.mela.command.binder;

import java.lang.annotation.Annotation;

public interface CommandNode {

  CommandNode group(String... aliases);

  CommandNode parent();

  CommandNode bind(Class<?> commandClass);

  <T extends Annotation> InterceptorBindingBuilder<T> interceptAt(Class<T> annotationType);

  <T extends Throwable> ExceptionBindingBuilder<T> handle(Class<T> exceptionType);

  <T> ParameterBindingBuilder<T> bindParameter(Class<T> parameterType);

}
