package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.Interceptor;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public interface InterceptorBindings {

  <T extends Annotation> Optional<Interceptor<T>> getInterceptor(Class<T> annotationType);

}
