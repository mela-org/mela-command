package com.github.stupremee.mela.command.binding.internal;

import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.ParameterBindingBuilder;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalParameterBindingBuilder<T> implements ParameterBindingBuilder<T> {

  private final InternalCommandBindingNode node;
  private final InjectableCommandTree tree;
  private final Multibinder<ArgumentMapper<?>> binder;
  private final TypeLiteral<T> parameterType;

  private Class<? extends Annotation> annotationType;

  InternalParameterBindingBuilder(InternalCommandBindingNode node, InjectableCommandTree tree,
                                         Multibinder<ArgumentMapper<?>> binder, TypeLiteral<T> parameterType) {
    this.node = node;
    this.tree = tree;
    this.binder = binder;
    this.parameterType = parameterType;
  }

  @Nonnull
  @Override
  public ParameterBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType) {
    this.annotationType = annotationType;
    return this;
  }

  @Nonnull
  @Override
  public CommandBindingNode toInstance(T instance) {
    return toMapper(ArgumentMapper.singleton(instance));
  }

  @Nonnull
  @SuppressWarnings("unchecked")
  @Override
  public CommandBindingNode toMapper(@Nonnull ArgumentMapper<T> mapper) {
    checkNotNull(mapper);
    binder.addBinding().toInstance(mapper);
    tree.addParameterBinding(createKey(), (Class<? extends ArgumentMapper<T>>) mapper.getClass());
    return node;
  }


  @Nonnull
  @Override
  public CommandBindingNode toMapper(@Nonnull Class<? extends ArgumentMapper<T>> clazz) {
    binder.addBinding().to(clazz);
    tree.addParameterBinding(createKey(), clazz);
    return node;
  }

  private Key<T> createKey() {
    return annotationType == null ? Key.get(parameterType) : Key.get(parameterType, annotationType);
  }
}
