package com.github.stupremee.mela.command.internal.empty;

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

  static <T> Optional<ArgumentMapper<T>> noMapper(Key<T> key) {
    return Optional.empty();
  }

  static <T extends Annotation> Optional<Interceptor<T>> noInterceptor(Class<T> type) {
    return Optional.empty();
  }

  static <T extends Throwable> Optional<ExceptionHandler<T>> noHandler(Class<T> type) {
    return Optional.empty();
  }
}
