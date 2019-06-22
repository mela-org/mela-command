package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.Interceptor;
import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalInterceptorBindings implements InterceptorBindings {

  private final Map<Class<? extends Annotation>, Interceptor<?>> bindings = Maps.newHashMap();

  @SuppressWarnings("unchecked") // type is always correct (map is encapsulated)
  @Override
  public <T extends Annotation> Optional<Interceptor<T>> getInterceptor(Class<T> annotationType) {
    return Optional.ofNullable((Interceptor<T>) bindings.get(annotationType));
  }

  <T extends Annotation> void put(Class<T> annotationType, Interceptor<T> interceptor) {
    bindings.put(annotationType, interceptor);
  }
}
