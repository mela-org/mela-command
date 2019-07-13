package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.DelegatedGroupBindings;
import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InjectableGroupBindings extends DelegatedGroupBindings {


  private InjectableGroupBindings(InjectableGroupBindings parent) {
    super(parent);
  }

  static InjectableGroupBindings create() {
    return new InjectableGroupBindings(null);
  }

  static InjectableGroupBindings childOf(InjectableGroupBindings parent) {
    return new InjectableGroupBindings(parent);
  }

  void assimilate(InjectableGroupBindings other) {
    this.interceptors.putAll(other.interceptors);
    this.handlers.putAll(other.handlers);
    this.mappers.putAll(other.mappers);
  }


  <T extends Annotation> void putInterceptor(Class<T> annotationType, Class<? extends Interceptor<T>> clazz) {
    interceptors.put(annotationType, new InterceptorBinding<>(clazz));
  }

  <T extends Throwable> void putHandler(Class<T> exceptionType, Class<? extends ExceptionHandler<T>> clazz) {
    handlers.put(exceptionType, new ExceptionBinding<>(clazz));
  }

  <T> void putMapper(Object placeholder, Class<? extends ArgumentMapper<T>> clazz) {
    // TODO: 11.07.2019
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

  private static final class InterceptorBinding<T extends Annotation> implements Supplier<Interceptor<T>> {
    final Class<? extends Interceptor<T>> clazz;
    Interceptor<T> interceptor;

    InterceptorBinding(Class<? extends Interceptor<T>> clazz) {
      this.clazz = clazz;
    }

    @Override
    public Interceptor<T> get() {
      return interceptor;
    }
  }

  private static final class ExceptionBinding<T extends Throwable> implements Supplier<ExceptionHandler<T>> {
    final Class<? extends ExceptionHandler<T>> clazz;
    ExceptionHandler<T> handler;

    ExceptionBinding(Class<? extends ExceptionHandler<T>> clazz) {
      this.clazz = clazz;
    }

    @Override
    public ExceptionHandler<T> get() {
      return handler;
    }
  }

  private static final class ParameterBinding<T> implements Supplier<ArgumentMapper<T>> {

    @Override
    public ArgumentMapper<T> get() {
      return null;
    }
  }

}
