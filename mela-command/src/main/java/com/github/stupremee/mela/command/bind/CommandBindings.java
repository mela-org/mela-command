package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.guice.annotation.Handlers;
import com.github.stupremee.mela.command.guice.annotation.Interceptors;
import com.github.stupremee.mela.command.guice.annotation.Mappers;
import com.google.inject.Inject;
import com.google.inject.Key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandBindings {

  public static final CommandBindings EMPTY = new CommandBindings(Map.of(), Map.of(), Map.of());

  private final Map<Class<? extends Annotation>,  CommandInterceptor<?>> interceptors;
  private final Map<Class<? extends Throwable>, ExceptionHandler<?>> handlers;
  private final Map<Key<?>, ArgumentMapper<?>> mappers;

  @Inject
  public CommandBindings(@Interceptors Map<Class<? extends Annotation>, CommandInterceptor<?>> interceptors,
                         @Handlers Map<Class<? extends Throwable>, ExceptionHandler<?>> handlers,
                         @Mappers Map<Key<?>, ArgumentMapper<?>> mappers) {
    this.interceptors = Map.copyOf(interceptors);
    this.handlers = Map.copyOf(handlers);
    this.mappers = Map.copyOf(mappers);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public final <T extends Annotation> CommandInterceptor<T> getInterceptor(@Nonnull Class<T> annotationType) {
    checkNotNull(annotationType);
    return (CommandInterceptor<T>) interceptors.get(annotationType);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public final <T extends Throwable> ExceptionHandler<T> getHandler(@Nonnull Class<T> exceptionType) {
    checkNotNull(exceptionType);
    Class<? super T> current = exceptionType;
    do {
      ExceptionHandler<T> binding = (ExceptionHandler<T>) handlers.get(exceptionType);
      if (binding != null)
        return binding;
      current = current.getSuperclass();
    } while (current != null);
    return null;
  }

  @SuppressWarnings("unchecked")
  public final <T> ArgumentMapper<T> getMapper(@Nonnull Key<T> key) {
    return (ArgumentMapper<T>) mappers.get(key);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;
    CommandBindings that = (CommandBindings) o;
    return Objects.equals(interceptors, that.interceptors) &&
        Objects.equals(handlers, that.handlers) &&
        Objects.equals(mappers, that.mappers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interceptors, handlers, mappers);
  }
}


