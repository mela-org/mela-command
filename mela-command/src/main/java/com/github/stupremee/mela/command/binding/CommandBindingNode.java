package com.github.stupremee.mela.command.binding;

import java.lang.annotation.Annotation;

public interface CommandBindingNode {

  CommandBindingNode group(String... aliases);

  CommandBindingNode parent();

  CommandBindingNode bind(Class<?> commandClass);

  <T extends Annotation> InterceptorBindingBuilder<T> interceptAt(Class<T> annotationType);

  <T extends Throwable> ExceptionBindingBuilder<T> handle(Class<T> exceptionType);

  <T> ParameterBindingBuilder<T> bindParameter(Class<T> parameterType);

}
