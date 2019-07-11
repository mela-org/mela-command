package com.github.stupremee.mela.command.bind.internal;

import com.github.stupremee.mela.command.bind.CommandBindingNode;
import com.github.stupremee.mela.command.bind.ParameterBindingBuilder;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 11.07.2019 adjust to parameter binding changes
final class InternalParameterBindingBuilder<T> implements ParameterBindingBuilder<T> {

  private final InternalCommandBindingNode node;
  private final CompilableGroup group;
  private final Multibinder<ArgumentMapper<?>> binder;
  private final TypeLiteral<T> parameterType;

  private Class<? extends Annotation> annotationType;

  InternalParameterBindingBuilder(InternalCommandBindingNode node, CompilableGroup group,
                                         Multibinder<ArgumentMapper<?>> binder, TypeLiteral<T> parameterType) {
    this.node = node;
    this.group = group;
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
    group.addParameterBinding(createKey(), (Class<? extends ArgumentMapper<T>>) mapper.getClass());
    return node;
  }


  @Nonnull
  @Override
  public CommandBindingNode toMapper(@Nonnull Class<? extends ArgumentMapper<T>> clazz) {
    binder.addBinding().to(clazz);
    group.addParameterBinding(createKey(), clazz);
    return node;
  }

  private Key<T> createKey() {
    return annotationType == null ? Key.get(parameterType) : Key.get(parameterType, annotationType);
  }
}
