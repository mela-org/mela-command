package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.inject.annotation.Commands;
import com.github.stupremee.mela.command.inject.annotation.Handlers;
import com.github.stupremee.mela.command.inject.annotation.Interceptors;
import com.github.stupremee.mela.command.inject.annotation.Mappers;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.inject.Inject;

import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class InjectionObjectHolder {

  private final Set<?> commandObjects;
  private final Set<ArgumentMapper<?>> mappers;
  private final Set<Interceptor<?>> interceptors;
  private final Set<ExceptionHandler<?>> handlers;

  @Inject
  public InjectionObjectHolder(@Commands Set<?> commandObjects,
                               @Mappers Set<ArgumentMapper<?>> mappers,
                               @Interceptors Set<Interceptor<?>> interceptors,
                               @Handlers Set<ExceptionHandler<?>> handlers) {
    this.commandObjects = commandObjects;
    this.mappers = mappers;
    this.interceptors = interceptors;
    this.handlers = handlers;
  }

  public Set<?> getCommandObjects() {
    return commandObjects;
  }

  public Set<ArgumentMapper<?>> getMappers() {
    return mappers;
  }

  public Set<Interceptor<?>> getInterceptors() {
    return interceptors;
  }

  public Set<ExceptionHandler<?>> getHandlers() {
    return handlers;
  }
}
