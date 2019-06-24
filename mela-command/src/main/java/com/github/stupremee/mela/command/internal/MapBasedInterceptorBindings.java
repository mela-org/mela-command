package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.binding.InterceptorBindings;
import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class MapBasedInterceptorBindings implements InterceptorBindings {

  private final Map<Class<? extends Annotation>, ValueWrapper<?>> bindings;

  MapBasedInterceptorBindings() {
    this.bindings = Maps.newHashMap();
  }

  private MapBasedInterceptorBindings(Map<Class<? extends Annotation>, ValueWrapper<?>> bindings) {
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

  void putAll(MapBasedInterceptorBindings bindings) {
    this.bindings.putAll(bindings.bindings);
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

  MapBasedInterceptorBindings copy() {
    return new MapBasedInterceptorBindings(bindings);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MapBasedInterceptorBindings that = (MapBasedInterceptorBindings) o;
    return Objects.equals(bindings, that.bindings);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(bindings);
  }

  private static final class ValueWrapper<T extends Annotation> {

    final Class<? extends Interceptor<T>> interceptorType;
    Interceptor<T> interceptor;

    ValueWrapper(Class<? extends Interceptor<T>> interceptorType) {
      this.interceptorType = interceptorType;
    }
  }
}
