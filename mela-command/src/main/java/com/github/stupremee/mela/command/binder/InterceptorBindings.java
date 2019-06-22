package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.interceptor.Interceptor;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public interface InterceptorBindings {

  <T extends Annotation> Optional<Interceptor<T>> getInterceptor(Class<T> annotationType);

}
