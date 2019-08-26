package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.intercept.CommandInterceptor;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class InterceptorBindingBuilder<T extends Annotation> {

  private final CommandBindingNode node;
  private final InjectableGroup group;
  private final Multibinder<CommandInterceptor<?>> binder;
  private final Class<T> annotationType;

  InterceptorBindingBuilder(CommandBindingNode node, InjectableGroup group,
                            Multibinder<CommandInterceptor<?>> binder, Class<T> annotationType) {
    this.node = node;
    this.group = group;
    this.binder = binder;
    this.annotationType = annotationType;
  }

  @Nonnull
  public CommandBindingNode with(@Nonnull Class<? extends CommandInterceptor<T>> clazz) {
    checkNotNull(clazz);
    group.addInterceptorBinding(annotationType, clazz);
    binder.addBinding().to(clazz);
    return node;
  }

  @Nonnull
  @SuppressWarnings("unchecked")
  public CommandBindingNode with(@Nonnull CommandInterceptor<T> interceptor) {
    checkNotNull(interceptor);
    group.addInterceptorBinding(annotationType, (Class<? extends CommandInterceptor<T>>) interceptor.getClass());
    binder.addBinding().toInstance(interceptor);
    return node;
  }
}
