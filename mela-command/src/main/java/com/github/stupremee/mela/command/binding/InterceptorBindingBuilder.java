package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.Interceptor;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

public interface InterceptorBindingBuilder<T extends Annotation> {

  @Nonnull
  CommandBindingNode with(@Nonnull Class<? extends Interceptor<T>> clazz);

  @Nonnull
  CommandBindingNode with(@Nonnull Interceptor<T> interceptor);

}
