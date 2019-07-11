package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.handle.ExceptionHandler;

import javax.annotation.Nonnull;

public interface ExceptionBindingBuilder<T extends Throwable> {

  @Nonnull
  CommandBindingNode with(@Nonnull Class<? extends ExceptionHandler<T>> clazz);

  @Nonnull
  CommandBindingNode with(@Nonnull ExceptionHandler<T> handler);

}
