package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;

public interface ExceptionBindingBuilder<T extends Throwable> {

  ExceptionBindingBuilder<T> ignoringInheritance();

  CommandBindingNode with(ExceptionHandler<T> handler);

}
