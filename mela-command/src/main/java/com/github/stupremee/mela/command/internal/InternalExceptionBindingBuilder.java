package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.ExceptionBindingBuilder;
import com.google.inject.multibindings.Multibinder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalExceptionBindingBuilder<T extends Throwable> implements ExceptionBindingBuilder<T> {

  private final InternalCommandBindingNode node;
  private final RecursiveCommandTree tree;
  private final Multibinder<ExceptionHandler<?>> binder;
  private final Class<T> exceptionType;

  private boolean ignoreInheritance = false;

  InternalExceptionBindingBuilder(InternalCommandBindingNode node, RecursiveCommandTree tree,
                                  Multibinder<ExceptionHandler<?>> binder, Class<T> exceptionType) {
    this.node = node;
    this.tree = tree;
    this.binder = binder;
    this.exceptionType = exceptionType;
  }

  @Override
  public ExceptionBindingBuilder<T> ignoringInheritance() {
    ignoreInheritance = true;
    return this;
  }

  @Override
  public CommandBindingNode with(Class<? extends ExceptionHandler<T>> clazz) {
    tree.addExceptionBinding(exceptionType, clazz, ignoreInheritance);
    binder.addBinding().to(clazz);
    return node;
  }

  @SuppressWarnings("unchecked")
  @Override
  public CommandBindingNode with(ExceptionHandler<T> handler) {
    tree.addExceptionBinding(exceptionType,
        (Class<? extends ExceptionHandler<T>>) handler.getClass(), ignoreInheritance);
    binder.addBinding().toInstance(handler);
    return node;
  }
}
