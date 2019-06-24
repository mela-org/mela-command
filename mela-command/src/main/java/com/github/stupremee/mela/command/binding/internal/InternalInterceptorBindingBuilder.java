package com.github.stupremee.mela.command.binding.internal;

import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.InterceptorBindingBuilder;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalInterceptorBindingBuilder<T extends Annotation> implements InterceptorBindingBuilder<T> {

  private final InternalCommandBindingNode node;
  private final InjectableCommandTree tree;
  private final Multibinder<Interceptor<?>> binder;
  private final Class<T> annotationType;

  InternalInterceptorBindingBuilder(InternalCommandBindingNode node, InjectableCommandTree tree,
                                    Multibinder<Interceptor<?>> binder, Class<T> annotationType) {
    this.node = node;
    this.tree = tree;
    this.binder = binder;
    this.annotationType = annotationType;
  }

  @Nonnull
  @Override
  public CommandBindingNode with(@Nonnull Class<? extends Interceptor<T>> clazz) {
    checkNotNull(clazz);
    tree.addInterceptorBinding(annotationType, clazz);
    binder.addBinding().to(clazz);
    return node;
  }

  @Nonnull
  @SuppressWarnings("unchecked")
  @Override
  public CommandBindingNode with(@Nonnull Interceptor<T> interceptor) {
    checkNotNull(interceptor);
    tree.addInterceptorBinding(annotationType, (Class<? extends Interceptor<T>>) interceptor.getClass());
    binder.addBinding().toInstance(interceptor);
    return node;
  }
}
