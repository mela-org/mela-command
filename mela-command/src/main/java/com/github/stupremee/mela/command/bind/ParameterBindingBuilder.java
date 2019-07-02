package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.map.ArgumentMapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

public interface ParameterBindingBuilder<T> {

  @Nonnull
  ParameterBindingBuilder<T> annotatedWith(@Nullable Class<? extends Annotation> annotationType);

  @Nonnull
  CommandBindingNode toInstance(T instance);

  @Nonnull
  CommandBindingNode toMapper(@Nonnull ArgumentMapper<T> provider);

  @Nonnull
  CommandBindingNode toMapper(@Nonnull Class<? extends ArgumentMapper<T>> clazz);

}
