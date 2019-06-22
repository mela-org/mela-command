package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.handler.ExceptionHandler;

import java.util.Optional;

public interface ExceptionBindings {

  <T extends Throwable> Optional<ExceptionHandler<T>> getHandler(Class<T> exceptionType);

}
