package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.handler.ExceptionHandler;

public interface ExceptionBindingBuilder<T extends Throwable> {

  ExceptionBindingBuilder<T> ignoringInheritance();

  void with(ExceptionHandler<T> handler);

}
