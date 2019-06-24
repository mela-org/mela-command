package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.ParameterBindingBuilder;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import java.lang.annotation.Annotation;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalParameterBindingBuilder<T> implements ParameterBindingBuilder<T> {

  private final InternalCommandBindingNode node;
  private final Multibinder<ArgumentMapper<?>> mapperBinder;
  private final RecursiveCommandTree tree;
  private final TypeLiteral<T> parameterType;

  private Class<? extends Annotation> annotationType;

  public InternalParameterBindingBuilder(InternalCommandBindingNode node, TypeLiteral<T> parameterType) {
    this.node = node;
    this.parameterType = parameterType;
    this.mapperBinder = node.getMultibinder().mapperBinder();
    this.tree = node.getTree();
  }

  @Override
  public ParameterBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType) {
    this.annotationType = annotationType;
    return this;
  }

  @Override
  public CommandBindingNode toInstance(T instance) {
    return toMapper(ArgumentMapper.singleton(instance));
  }

  @SuppressWarnings("unchecked")
  @Override
  public CommandBindingNode toMapper(ArgumentMapper<T> mapper) {
    mapperBinder.addBinding().toInstance(mapper);
    tree.addParameterBinding(createKey(), (Class<? extends ArgumentMapper<T>>) mapper.getClass());
    return node;
  }


  @Override
  public CommandBindingNode toMapper(Class<? extends ArgumentMapper<T>> clazz) {
    mapperBinder.addBinding().to(clazz);
    tree.addParameterBinding(createKey(), clazz);
    return node;
  }

  private Key<T> createKey() {
    return annotationType == null ? Key.get(parameterType) : Key.get(parameterType, annotationType);
  }
}
