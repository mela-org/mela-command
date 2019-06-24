package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;

import javax.annotation.Nonnull;

public interface ExceptionBindingBuilder<T extends Throwable> {

  @Nonnull
  ExceptionBindingBuilder<T> ignoringInheritance();

  @Nonnull
  CommandBindingNode with(@Nonnull Class<? extends ExceptionHandler<T>> clazz);

  @Nonnull
  CommandBindingNode with(@Nonnull ExceptionHandler<T> handler);

}
