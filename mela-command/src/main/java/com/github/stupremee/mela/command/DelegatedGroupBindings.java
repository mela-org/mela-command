package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.inject.Key;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class DelegatedGroupBindings implements GroupBindings {

  private final DelegatedGroupBindings parent;
  protected final Map<Class<? extends Annotation>, Supplier<? extends Interceptor<?>>> interceptors;
  protected final Map<Class<? extends Throwable>, Supplier<? extends ExceptionHandler<?>>> handlers;
  protected final Map<Key<?>, Supplier<? extends ArgumentMapper<?>>> mappers;

  protected DelegatedGroupBindings(DelegatedGroupBindings parent) {
    this.parent = parent;
    this.interceptors = Maps.newHashMap();
    this.handlers = Maps.newHashMap();
    this.mappers = Maps.newHashMap();
  }

  @SuppressWarnings("unchecked")
  @Nullable
  @Override
  public final <T extends Annotation> Interceptor<T> getInterceptor(@Nonnull Class<T> annotationType) {
    return (Interceptor<T>) getIfPresent(
        findBinding((bindings) -> bindings.interceptors.get(annotationType))
    );
  }

  @SuppressWarnings("unchecked")
  @Nullable
  @Override
  public final <T extends Throwable> ExceptionHandler<T> getHandler(@Nonnull Class<T> exceptionType) {
    checkNotNull(exceptionType);
    Supplier<ExceptionHandler<T>> binding = findBinding(
        (bindings) -> (Supplier<ExceptionHandler<T>>) bindings.handlers.get(exceptionType));
    if (binding == null) {
      Class<? super T> superclass = exceptionType.getSuperclass();
      while (superclass != null) {
        final Class<? super T> clazz = superclass;
        Supplier<ExceptionHandler<T>> next = findBinding(
            (bindings) -> (Supplier<ExceptionHandler<T>>) bindings.handlers.get(clazz));
        if (next != null)
          return next.get();
        superclass = superclass.getSuperclass();
      }
    } else {
      return binding.get();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public final <T> ArgumentMapper<T> getMapper(@Nonnull Key<T> key) {
    return (ArgumentMapper<T>) getIfPresent(findBinding((bindings) -> bindings.mappers.get(key)));
  }

  private <T> T getIfPresent(Supplier<T> supplier) {
    return supplier != null ? supplier.get() : null;
  }

  private <T> T findBinding(Function<DelegatedGroupBindings, T> retriever) {
    DelegatedGroupBindings bindings = this;
    while (bindings != null) {
      T binding = retriever.apply(bindings);
      if (binding != null)
        return binding;
      bindings = bindings.parent;
    }
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;
    DelegatedGroupBindings that = (DelegatedGroupBindings) o;
    return Objects.equals(interceptors, that.interceptors) &&
        Objects.equals(handlers, that.handlers) &&
        Objects.equals(mappers, that.mappers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(interceptors, handlers, mappers);
  }
}


