package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.inject.annotation.Commands;
import com.github.stupremee.mela.command.inject.annotation.Handlers;
import com.github.stupremee.mela.command.inject.annotation.Interceptors;
import com.github.stupremee.mela.command.inject.annotation.Mappers;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class CommandMultibinder {

  private static final TypeLiteral<ArgumentMapper<?>> MAPPER_LITERAL = new TypeLiteral<>() {};
  private static final TypeLiteral<Interceptor<?>> INTERCEPTOR_LITERAL = new TypeLiteral<>() {};
  private static final TypeLiteral<ExceptionHandler<?>> HANDLER_LITERAL = new TypeLiteral<>() {};

  private final Binder binder;

  private Multibinder<ArgumentMapper<?>> mapperBinder;
  private Multibinder<Object> commandObjectBinder;
  private Multibinder<Interceptor<?>> interceptorBinder;
  private Multibinder<ExceptionHandler<?>> handlerBinder;

  CommandMultibinder(Binder binder) {
    this.binder = binder;
  }

  void addMapper(ArgumentMapper<?> mapper) {
    mapperBinder().addBinding().toInstance(mapper);
  }

  void addMapper(Class<? extends ArgumentMapper<?>> clazz) {
    mapperBinder().addBinding().to(clazz);
  }

  void addHandler(ExceptionHandler<?> handler) {
    handlerBinder().addBinding().toInstance(handler);
  }

  void addHandler(Class<? extends ExceptionHandler<?>> clazz) {
    handlerBinder().addBinding().to(clazz);
  }

  void addInterceptor(Interceptor<?> interceptor) {
    interceptorBinder().addBinding().toInstance(interceptor);
  }

  void addInterceptor(Class<? extends Interceptor<?>> clazz) {
    interceptorBinder().addBinding().to(clazz);
  }

  void addCommand(Object command) {
    commandObjectBinder().addBinding().toInstance(command);
  }

  void addCommand(Class<?> clazz) {
    commandObjectBinder().addBinding().to(clazz);
  }

  private Multibinder<Object> commandObjectBinder() {
    if (commandObjectBinder == null)
      commandObjectBinder = Multibinder.newSetBinder(binder, Object.class, Commands.class);
    return commandObjectBinder;
  }

  private Multibinder<ArgumentMapper<?>> mapperBinder() {
    if (mapperBinder == null)
      mapperBinder = Multibinder.newSetBinder(binder, MAPPER_LITERAL, Mappers.class);
    return mapperBinder;
  }

  private Multibinder<Interceptor<?>> interceptorBinder() {
    if (interceptorBinder == null)
      interceptorBinder = Multibinder.newSetBinder(binder, INTERCEPTOR_LITERAL, Interceptors.class);
    return interceptorBinder;
  }

  private Multibinder<ExceptionHandler<?>> handlerBinder() {
    if (handlerBinder == null)
      handlerBinder = Multibinder.newSetBinder(binder, HANDLER_LITERAL, Handlers.class);
    return handlerBinder;
  }

}
