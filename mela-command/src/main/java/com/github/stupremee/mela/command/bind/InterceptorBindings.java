package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.intercept.Interceptor;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public interface InterceptorBindings {

  @Nonnull
  <T extends Annotation> Optional<Interceptor<T>> getInterceptor(Class<T> annotationType);

}
