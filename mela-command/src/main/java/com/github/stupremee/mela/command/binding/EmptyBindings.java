package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.inject.Key;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class EmptyBindings {

  public static final ParameterBindings PARAMETER_BINDINGS = EmptyBindings::noMapper;
  public static final InterceptorBindings INTERCEPTOR_BINDINGS = EmptyBindings::noInterceptor;
  public static final ExceptionBindings EXCEPTION_BINDINGS = EmptyBindings::noHandler;

  private static <T> Optional<ArgumentMapper<T>> noMapper(Key<T> key) {
    return Optional.empty();
  }

  private static <T extends Annotation> Optional<Interceptor<T>> noInterceptor(Class<T> type) {
    return Optional.empty();
  }

  private static <T extends Throwable> Optional<ExceptionHandler<T>> noHandler(Class<T> type) {
    return Optional.empty();
  }
}
