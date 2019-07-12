package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ExceptionBindingBuilder<T extends Throwable> {

  private final CommandBindingNode node;
  private final InjectableGroup group;
  private final Multibinder<ExceptionHandler<?>> binder;
  private final Class<T> exceptionType;

  ExceptionBindingBuilder(CommandBindingNode node, InjectableGroup group,
                          Multibinder<ExceptionHandler<?>> binder, Class<T> exceptionType) {
    this.node = node;
    this.group = group;
    this.binder = binder;
    this.exceptionType = exceptionType;
  }
  @Nonnull
  public CommandBindingNode with(@Nonnull Class<? extends ExceptionHandler<T>> clazz) {
    checkNotNull(clazz);
    group.addExceptionBinding(exceptionType, clazz);
    binder.addBinding().to(clazz);
    return node;
  }

  @Nonnull
  @SuppressWarnings("unchecked")
  public CommandBindingNode with(@Nonnull ExceptionHandler<T> handler) {
    checkNotNull(handler);
    group.addExceptionBinding(exceptionType, (Class<? extends ExceptionHandler<T>>) handler.getClass());
    binder.addBinding().toInstance(handler);
    return node;
  }
}
