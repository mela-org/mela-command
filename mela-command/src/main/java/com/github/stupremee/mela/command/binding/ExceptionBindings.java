package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;

import java.util.Optional;

public interface ExceptionBindings {

  <T extends Throwable> Optional<ExceptionHandler<T>> getHandler(Class<T> exceptionType);

}
