package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.CommandContext;

import javax.annotation.Nonnull;

public interface ExceptionHandler<T extends Throwable> {

  void handle(@Nonnull Throwable exception, @Nonnull CommandContext context); // TODO: 24.06.2019 replace with actual logic

}
