package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.handle.ExceptionHandler;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface ExceptionBindings {

  @Nonnull
  <T extends Throwable> Optional<ExceptionHandler<T>> getHandler(Class<T> exceptionType);

}
