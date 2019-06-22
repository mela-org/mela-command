package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.inject.TypeLiteral;

import java.lang.annotation.Annotation;
import java.util.Optional;

public interface ParameterBindings {

  <T> Optional<ArgumentMapper<T>> getMapper(Key<T> key);

  class Key<T> {

    // TODO: 22.06.2019 cache, equals/hashcode

    private final TypeLiteral<T> type;
    private final Class<? extends Annotation> annotationType;

    private Key(TypeLiteral<T> type, Class<? extends Annotation> annotationType) {
      this.type = type;
      this.annotationType = annotationType;
    }

    public static <T> Key<T> get(Class<T> type) {
      return get(type, null);
    }

    public static <T> Key<T> get(Class<T> type, Class<? extends Annotation> annotationType) {
      return get(TypeLiteral.get(type), annotationType);
    }

    public static <T> Key<T> get(TypeLiteral<T> literal) {
      return get(literal, null);
    }

    public static <T> Key<T> get(TypeLiteral<T> literal, Class<? extends Annotation> annotationType) {
      return null;
    }
  }
}
