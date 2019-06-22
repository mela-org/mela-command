package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.mapping.ArgumentMapper;

import java.lang.annotation.Annotation;

public interface ParameterBindingBuilder<T> {

  ParameterBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType);

  CommandNode toInstance(T instance);

  CommandNode toProvider(ArgumentMapper<T> provider);

}
