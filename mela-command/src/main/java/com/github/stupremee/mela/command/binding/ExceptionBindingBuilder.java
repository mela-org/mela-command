package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;

public interface ExceptionBindingBuilder<T extends Throwable> {

  ExceptionBindingBuilder<T> ignoringInheritance();

  CommandBindingNode with(Class<? extends ExceptionHandler<T>> clazz);

  CommandBindingNode withInstance(ExceptionHandler<T> handler);

}
