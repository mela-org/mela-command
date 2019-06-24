package com.github.stupremee.mela.command.binding;

import com.google.inject.TypeLiteral;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

public interface CommandBindingNode {

  @Nonnull
  CommandBindingNode group(@Nonnull String... aliases);

  CommandBindingNode parent();

  @Nonnull
  CommandBindingNode add(@Nonnull Class<?> commandClass);

  @Nonnull
  CommandBindingNode add(@Nonnull Object command);

  @Nonnull
  <T extends Annotation> InterceptorBindingBuilder<T> interceptAt(@Nonnull Class<T> annotationType);

  @Nonnull
  <T extends Throwable> ExceptionBindingBuilder<T> handle(@Nonnull Class<T> exceptionType);

  @Nonnull
  <T> ParameterBindingBuilder<T> bindParameter(@Nonnull Class<T> parameterType);

  <T> ParameterBindingBuilder<T> bindParameter(@Nonnull TypeLiteral<T> literal);

}
