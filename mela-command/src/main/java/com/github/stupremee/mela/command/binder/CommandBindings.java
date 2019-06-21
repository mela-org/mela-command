package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.handler.ExceptionHandler;
import com.github.stupremee.mela.command.interceptor.Interceptor;
import com.github.stupremee.mela.command.provider.ArgumentProvider;

import java.lang.annotation.Annotation;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// @ImplementedBy
public interface CommandBindings {

  <T> ArgumentProvider<T> getProvider(Class<T> parameterType);

  <T> ArgumentProvider<T> getProvider(Class<T> parameterType, Class<? extends Annotation> annotationType);

  <T> ArgumentProvider<T> getProvider(ParameterBindingKey<T> key);

  <T extends Annotation> Interceptor<T> getInterceptor(Class<T> annotationType);

  <T extends Throwable> ExceptionHandler<T> getHandler(Class<T> exceptionType);

}
