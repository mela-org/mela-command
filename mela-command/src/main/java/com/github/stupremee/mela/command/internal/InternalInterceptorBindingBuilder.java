package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.InterceptorBindingBuilder;
import com.google.inject.multibindings.Multibinder;

import java.lang.annotation.Annotation;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalInterceptorBindingBuilder<T extends Annotation> implements InterceptorBindingBuilder<T> {

  private final InternalCommandBindingNode node;
  private final RecursiveCommandTree tree;
  private final Multibinder<Interceptor<?>> binder;
  private final Class<T> annotationType;

  InternalInterceptorBindingBuilder(InternalCommandBindingNode node, RecursiveCommandTree tree,
                                    Multibinder<Interceptor<?>> binder, Class<T> annotationType) {
    this.node = node;
    this.tree = tree;
    this.binder = binder;
    this.annotationType = annotationType;
  }

  @Override
  public CommandBindingNode with(Class<? extends Interceptor<T>> clazz) {
    tree.addInterceptorBinding(annotationType, clazz);
    binder.addBinding().to(clazz);
    return node;
  }

  @SuppressWarnings("unchecked")
  @Override
  public CommandBindingNode with(Interceptor<T> interceptor) {
    tree.addInterceptorBinding(annotationType, (Class<? extends Interceptor<T>>) interceptor.getClass());
    binder.addBinding().toInstance(interceptor);
    return node;
  }
}
