package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.intercept.Interceptor;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class InterceptorBindingBuilder<T extends Annotation> {

  private final CommandBindingNode node;
  private final CompilableGroup group;
  private final Multibinder<Interceptor<?>> binder;
  private final Class<T> annotationType;

  InterceptorBindingBuilder(CommandBindingNode node, CompilableGroup group,
                            Multibinder<Interceptor<?>> binder, Class<T> annotationType) {
    this.node = node;
    this.group = group;
    this.binder = binder;
    this.annotationType = annotationType;
  }

  @Nonnull
  public CommandBindingNode with(@Nonnull Class<? extends Interceptor<T>> clazz) {
    checkNotNull(clazz);
    group.addInterceptorBinding(annotationType, clazz);
    binder.addBinding().to(clazz);
    return node;
  }

  @Nonnull
  @SuppressWarnings("unchecked")
  public CommandBindingNode with(@Nonnull Interceptor<T> interceptor) {
    checkNotNull(interceptor);
    group.addInterceptorBinding(annotationType, (Class<? extends Interceptor<T>>) interceptor.getClass());
    binder.addBinding().toInstance(interceptor);
    return node;
  }
}
