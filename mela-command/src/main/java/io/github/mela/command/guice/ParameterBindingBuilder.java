package io.github.mela.command.guice;

import io.github.mela.command.bind.ArgumentMapper;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ParameterBindingBuilder<T> {

  private final CommandBindingNode node;
  private final MapBinder<Key<?>, ArgumentMapper<?>> binder;
  private final TypeLiteral<T> parameterType;
  private Class<? extends Annotation> annotationType;

  ParameterBindingBuilder(CommandBindingNode node,
                          MapBinder<Key<?>, ArgumentMapper<?>> binder, TypeLiteral<T> parameterType) {
    this.node = node;
    this.binder = binder;
    this.parameterType = parameterType;
  }

  @Nonnull
  public ParameterBindingBuilder<T> annotatedWith(Class<? extends Annotation> annotationType) {
    this.annotationType = annotationType;
    return this;
  }

  @Nonnull
  public CommandBindingNode toInstance(T instance) {
    return toMapper(ArgumentMapper.singleton(instance));
  }

  @Nonnull
  public CommandBindingNode toMapper(@Nonnull ArgumentMapper<T> mapper) {
    checkNotNull(mapper);
    binder.addBinding(createKey()).toInstance(mapper);
    return node;
  }


  @Nonnull
  public CommandBindingNode toMapper(@Nonnull Class<? extends ArgumentMapper<T>> clazz) {
    binder.addBinding(createKey()).to(clazz);
    return node;
  }

  private Key<T> createKey() {
    return annotationType == null ? Key.get(parameterType) : Key.get(parameterType, annotationType);
  }
}
