package com.github.stupremee.mela.command.binding;

import com.google.inject.TypeLiteral;

import java.lang.annotation.Annotation;

public interface CommandBindingNode {

  CommandBindingNode group(String... aliases);

  CommandBindingNode parent();

  CommandBindingNode add(Class<?> commandClass);

  CommandBindingNode add(Object command);

  <T extends Annotation> InterceptorBindingBuilder<T> interceptAt(Class<T> annotationType);

  <T extends Throwable> ExceptionBindingBuilder<T> handle(Class<T> exceptionType);

  <T> ParameterBindingBuilder<T> bindParameter(Class<T> parameterType);

  <T> ParameterBindingBuilder<T> bindParameter(TypeLiteral<T> literal);

}
