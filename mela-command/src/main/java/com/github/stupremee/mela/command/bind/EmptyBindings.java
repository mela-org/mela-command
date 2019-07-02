package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Key;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class EmptyBindings implements ParameterBindings, InterceptorBindings, ExceptionBindings {

  public static final EmptyBindings INSTANCE = new EmptyBindings();

  private EmptyBindings() {}

  @Override
  public <T extends Throwable> Optional<ExceptionHandler<T>> getHandler(Class<T> exceptionType) {
    return Optional.empty();
  }

  @Override
  public <T extends Annotation> Optional<Interceptor<T>> getInterceptor(Class<T> annotationType) {
    return Optional.empty();
  }

  @Override
  public <T> Optional<ArgumentMapper<T>> getMapper(Key<T> key) {
    return Optional.empty();
  }
}
