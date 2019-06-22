package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.Interceptor;
import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalInterceptorBindings implements InterceptorBindings {

  private final Map<Class<? extends Annotation>, ValueWrapper<?>> bindings;

  private InternalInterceptorBindings() {
    this.bindings = Maps.newHashMap();
  }

  private InternalInterceptorBindings(Map<Class<? extends Annotation>, ValueWrapper<?>> bindings) {
    this.bindings = Maps.newHashMap(bindings);
  }

  @SuppressWarnings("unchecked") // type is always correct (map is encapsulated)
  @Override
  public <T extends Annotation> Optional<Interceptor<T>> getInterceptor(Class<T> annotationType) {
    return Optional.ofNullable((ValueWrapper<T>) bindings.get(annotationType))
        .map((value) -> value.interceptor);
  }

  <T extends Annotation> void put(Class<T> annotationType, Class<? extends Interceptor<T>> interceptorType) {
    bindings.put(annotationType, new ValueWrapper<>(interceptorType));
  }

  void inject(Set<? extends Interceptor<?>> interceptors) {
    for (ValueWrapper value : bindings.values()) {
      for (Interceptor interceptor : interceptors) {
        if (interceptor.getClass() == value.interceptorType) {
          value.interceptor = interceptor;
        }
      }
    }
  }

  InternalInterceptorBindings copy() {
    return new InternalInterceptorBindings(bindings);
  }

  private static final class ValueWrapper<T extends Annotation> {

    final Class<? extends Interceptor<T>> interceptorType;
    Interceptor<T> interceptor;

    ValueWrapper(Class<? extends Interceptor<T>> interceptorType) {
      this.interceptorType = interceptorType;
    }
  }
}
