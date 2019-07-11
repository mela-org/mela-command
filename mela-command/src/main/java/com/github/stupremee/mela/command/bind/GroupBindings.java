package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Key;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

public interface GroupBindings {

  @Nullable
  <T extends Annotation> Interceptor<T> getInterceptor(Class<T> annotationType);

  @Nullable
  <T extends Throwable> ExceptionHandler<T> getHandler(Class<T> exceptionType);

  // TODO: 11.07.2019  
  <T> ArgumentMapper<T> getMapper(Key<T> key);

}
