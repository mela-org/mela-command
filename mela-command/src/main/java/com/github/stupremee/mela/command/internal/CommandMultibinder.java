package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.inject.Commands;
import com.github.stupremee.mela.command.inject.Handlers;
import com.github.stupremee.mela.command.inject.Interceptors;
import com.github.stupremee.mela.command.inject.Mappers;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandMultibinder {

  private static final TypeLiteral<ArgumentMapper<?>> MAPPER_LITERAL = new TypeLiteral<>() {};
  private static final TypeLiteral<Interceptor<?>> INTERCEPTOR_LITERAL = new TypeLiteral<>() {};
  private static final TypeLiteral<ExceptionHandler<?>> HANDLER_LITERAL = new TypeLiteral<>() {};

  private final Binder binder;

  private Multibinder<ArgumentMapper<?>> mapperBinder;
  private Multibinder<Object> commandObjectBinder;
  private Multibinder<Interceptor<?>> interceptorBinder;
  private Multibinder<ExceptionHandler<?>> handlerBinder;

  public CommandMultibinder(Binder binder) {
    this.binder = binder;
    commandObjectBinder = Multibinder.newSetBinder(binder, Object.class, Commands.class);
    mapperBinder = Multibinder.newSetBinder(binder, MAPPER_LITERAL, Mappers.class);
    interceptorBinder = Multibinder.newSetBinder(binder, INTERCEPTOR_LITERAL, Interceptors.class);
    handlerBinder = Multibinder.newSetBinder(binder, HANDLER_LITERAL, Handlers.class);
  }

  public Multibinder<Object> commandObjectBinder() {
    return commandObjectBinder;
  }

  public Multibinder<ArgumentMapper<?>> mapperBinder() {
    return mapperBinder;
  }

  public Multibinder<Interceptor<?>> interceptorBinder() {
    return interceptorBinder;
  }

  public Multibinder<ExceptionHandler<?>> handlerBinder() {
    return handlerBinder;
  }

  public Binder binder() {
    return binder;
  }

}