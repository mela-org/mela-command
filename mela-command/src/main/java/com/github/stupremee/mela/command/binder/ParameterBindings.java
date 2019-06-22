package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.mapping.ArgumentMapper;

import java.lang.annotation.Annotation;
import java.util.Optional;

public interface ParameterBindings {

  <T> Optional<ArgumentMapper<T>> getMapper(Key<T> key);

  class Key<T> {

    private final Class<T> type;
    private final Class<? extends Annotation> annotationType;

    private Key(Class<T> type, Class<? extends Annotation> annotationType) {
      this.type = type;
      this.annotationType = annotationType;
    }

    public static <T> Key<T> get(Class<T> type) {
      return get(type, null);
    }

    public static <T> Key<T> get(Class<T> type, Class<? extends Annotation> annotationType) {
      return null;
    }
  }
}
