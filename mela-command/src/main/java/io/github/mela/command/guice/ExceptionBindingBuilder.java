package io.github.mela.command.guice;

import io.github.mela.command.bind.ExceptionHandler;
import com.google.inject.multibindings.MapBinder;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ExceptionBindingBuilder<T extends Throwable> {

  private final CommandBindingNode node;
  private final MapBinder<Class<? extends Throwable>, ExceptionHandler<?>> binder;
  private final Class<T> exceptionType;

  ExceptionBindingBuilder(CommandBindingNode node,
                          MapBinder<Class<? extends Throwable>, ExceptionHandler<?>> binder,
                          Class<T> exceptionType) {
    this.node = node;
    this.binder = binder;
    this.exceptionType = exceptionType;
  }

  @Nonnull
  public CommandBindingNode with(@Nonnull Class<? extends ExceptionHandler<T>> clazz) {
    checkNotNull(clazz);
    binder.addBinding(exceptionType).to(clazz);
    return node;
  }

  @Nonnull
  public CommandBindingNode with(@Nonnull ExceptionHandler<T> handler) {
    checkNotNull(handler);
    binder.addBinding(exceptionType).toInstance(handler);
    return node;
  }
}
