package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.interceptor.Interceptor;

import java.lang.annotation.Annotation;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// @ImplementedBy
public interface InterceptorBindings {

  <T extends Annotation> Interceptor<T> getInterceptor(Class<T> annotationType);

}
