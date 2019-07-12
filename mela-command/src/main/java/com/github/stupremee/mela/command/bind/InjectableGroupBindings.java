package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.GroupBindings;
import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.common.collect.Maps;
import com.google.inject.Key;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InjectableGroupBindings implements GroupBindings {

  private final InjectableGroupBindings parent;

  private final Map<Class<? extends Annotation>, InterceptorBinding<?>> interceptorBindings;
  private final Map<Class<? extends Throwable>, ExceptionBinding<?>> exceptionBindings;
  private final Map<Object, ParameterBinding<?>> parameterBindings;

  private InjectableGroupBindings(InjectableGroupBindings parent) {
    this.parent = parent;
    this.interceptorBindings = Maps.newHashMap();
    this.exceptionBindings = Maps.newTreeMap((one, two) -> {
      if (one == two) {
        return 0;
      } else if (one.isAssignableFrom(two)) {
        return 1;
      } else {
        return -1;
      }
    });
    this.parameterBindings = Maps.newHashMap();
  }

  static InjectableGroupBindings create() {
    return new InjectableGroupBindings(null);
  }

  static InjectableGroupBindings childOf(InjectableGroupBindings parent) {
    return new InjectableGroupBindings(parent);
  }

  void assimilate(InjectableGroupBindings other) {
    this.interceptorBindings.putAll(other.interceptorBindings);
    this.exceptionBindings.putAll(other.exceptionBindings);
    this.parameterBindings.putAll(other.parameterBindings);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends Annotation> Interceptor<T> getInterceptor(Class<T> annotationType) {
    return findBinding((bindings) -> {
      InterceptorBinding<T> binding = (InterceptorBinding<T>) bindings.interceptorBindings.get(annotationType);
      return binding != null ? binding.interceptor : null;
    });
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends Throwable> ExceptionHandler<T> getHandler(Class<T> exceptionType) {
    return findBinding((bindings) -> {
      ExceptionBinding<T> binding = (ExceptionBinding<T>) bindings.exceptionBindings.get(exceptionType);
      return binding != null ? binding.handler : bindings.findSuperclassHandler(exceptionType);
    });
  }

  @SuppressWarnings("unchecked")
  private <T extends Throwable> ExceptionHandler<T> findSuperclassHandler(Class<T> exceptionType) {
    Class<?> superclass = exceptionType;
    while ((superclass = superclass.getSuperclass()) != null) {
      ExceptionBinding<T> binding = (ExceptionBinding<T>) exceptionBindings.get(superclass);
      if (binding != null)
        return binding.handler;
    }
    return null;
  }

  @Override
  public <T> ArgumentMapper<T> getMapper(Key<T> key) {
    return findBinding((bindings) -> bindings.parameterBindings.get())
  }

  <T extends Annotation> void putInterceptor(Class<T> annotationType, Class<? extends Interceptor<T>> clazz) {
    interceptorBindings.put(annotationType, new InterceptorBinding<>(clazz));
  }

  <T extends Throwable> void putHandler(Class<T> exceptionType, Class<? extends ExceptionHandler<T>> clazz) {
    exceptionBindings.put(exceptionType, new ExceptionBinding<>(clazz));
  }

  <T> void putMapper(Object placeholder, Class<? extends ArgumentMapper<T>> clazz) {
    // TODO: 11.07.2019
  }

  private <T> T findBinding(Function<InjectableGroupBindings, T> retriever) {
    InjectableGroupBindings bindings = this;
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
    InjectableGroupBindings that = (InjectableGroupBindings) o;
    return Objects.equals(parameterBindings, that.parameterBindings) &&
        Objects.equals(exceptionBindings, that.exceptionBindings) &&
        Objects.equals(interceptorBindings, that.interceptorBindings);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parameterBindings, exceptionBindings, interceptorBindings);
  }


  void inject(Set<Interceptor<?>> interceptors,
              Set<ExceptionHandler<?>> handlers,
              Set<ArgumentMapper<?>> mappers) {
    injectInterceptors(interceptors);
    injectHandlers(handlers);
    injectMappers(mappers);
  }

  // TODO: 11.07.2019
  private void injectInterceptors(Set<Interceptor<?>> interceptors) {

  }

  private void injectHandlers(Set<ExceptionHandler<?>> handlers) {

  }

  private void injectMappers(Set<ArgumentMapper<?>> mappers) {

  }

  private static final class InterceptorBinding<T extends Annotation> {
    final Class<? extends Interceptor<T>> clazz;
    Interceptor<T> interceptor;

    InterceptorBinding(Class<? extends Interceptor<T>> clazz) {
      this.clazz = clazz;
    }
  }

  private static final class ExceptionBinding<T extends Throwable> {
    final Class<? extends ExceptionHandler<T>> clazz;
    ExceptionHandler<T> handler;

    ExceptionBinding(Class<? extends ExceptionHandler<T>> clazz) {
      this.clazz = clazz;
    }
  }

  private static final class ParameterBinding<T> {

  }

}
