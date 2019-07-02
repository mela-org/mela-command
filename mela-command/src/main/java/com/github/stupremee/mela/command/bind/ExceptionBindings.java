package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.handle.ExceptionHandler;

import java.util.Optional;

public interface ExceptionBindings {

  <T extends Throwable> Optional<ExceptionHandler<T>> getHandler(Class<T> exceptionType);

}
