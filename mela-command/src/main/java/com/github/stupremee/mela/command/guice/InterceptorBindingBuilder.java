package com.github.stupremee.mela.command.guice;

import com.github.stupremee.mela.command.bind.CommandInterceptor;
import com.google.inject.multibindings.MapBinder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class InterceptorBindingBuilder<T extends Annotation> {

  private final CommandBindingNode node;
  private final MapBinder<Class<? extends Annotation>, CommandInterceptor<?>> binder;
  private final Class<T> annotationType;

  InterceptorBindingBuilder(CommandBindingNode node,
                            MapBinder<Class<? extends Annotation>, CommandInterceptor<?>> binder, Class<T> annotationType) {
    this.node = node;
    this.binder = binder;
    this.annotationType = annotationType;
  }

  @Nonnull
  public CommandBindingNode with(@Nonnull Class<? extends CommandInterceptor<T>> clazz) {
    checkNotNull(clazz);
    binder.addBinding(annotationType).to(clazz);
    return node;
  }

  @Nonnull
  public CommandBindingNode with(@Nonnull CommandInterceptor<T> interceptor) {
    checkNotNull(interceptor);
    binder.addBinding(annotationType).toInstance(interceptor);
    return node;
  }
}
