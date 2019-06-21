package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.handler.ExceptionHandler;

// @ImplementedBy
public interface ExceptionBindings {

  <T extends Throwable> ExceptionHandler<T> getHandler(Class<T> exceptionType);

}
