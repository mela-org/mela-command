package io.github.mela.command.bind;

import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface ExceptionHandler<T extends Throwable> {

  void handle(@Nonnull T exception, @Nonnull CommandContext context);

}
