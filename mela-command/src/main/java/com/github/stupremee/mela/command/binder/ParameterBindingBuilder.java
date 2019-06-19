package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.provider.ArgumentProvider;

import java.lang.annotation.Annotation;

public interface ParameterBindingBuilder<T> {

  ParameterBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType);

  void toInstance(T instance);

  void toProvider(ArgumentProvider<T> provider);

}
