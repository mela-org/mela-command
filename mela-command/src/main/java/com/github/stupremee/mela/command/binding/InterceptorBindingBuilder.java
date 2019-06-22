package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.Interceptor;

import java.lang.annotation.Annotation;

public interface InterceptorBindingBuilder<T extends Annotation> {

  CommandBindingNode with(Class<? extends Interceptor<T>> clazz);

  CommandBindingNode withInstance(Interceptor<T> interceptor);

}
