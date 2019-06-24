package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.ParameterBindingBuilder;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;

import java.lang.annotation.Annotation;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class InternalParameterBindingBuilder<T> implements ParameterBindingBuilder<T> {


  @Override
  public ParameterBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType) {
    return null;
  }

  @Override
  public CommandBindingNode toInstance(T instance) {
    return null;
  }

  @Override
  public CommandBindingNode toMapper(ArgumentMapper<T> provider) {
    return null;
  }

  @Override
  public CommandBindingNode toMapper(Class<? extends ArgumentMapper<T>> clazz) {
    return null;
  }
}
