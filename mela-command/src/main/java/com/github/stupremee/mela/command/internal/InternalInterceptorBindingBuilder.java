package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.InterceptorBindingBuilder;

import java.lang.annotation.Annotation;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalInterceptorBindingBuilder<T extends Annotation> implements InterceptorBindingBuilder<T> {

  private final InternalCommandBindingNode node;
  private final Class<T> annotationType;

  InternalInterceptorBindingBuilder(InternalCommandBindingNode node, Class<T> annotationType) {
    this.node = node;
    this.annotationType = annotationType;
  }

  @Override
  public CommandBindingNode with(Class<? extends Interceptor<T>> clazz) {
    node.getTree().addInterceptorBinding(annotationType, clazz);
    node.getMultibinder().interceptorBinder().addBinding().to(clazz);
    return node;
  }

  @SuppressWarnings("unchecked")
  @Override
  public CommandBindingNode with(Interceptor<T> interceptor) {
    node.getTree().addInterceptorBinding(annotationType, (Class<? extends Interceptor<T>>) interceptor.getClass());
    node.getMultibinder().interceptorBinder().addBinding().toInstance(interceptor);
    return node;
  }
}
