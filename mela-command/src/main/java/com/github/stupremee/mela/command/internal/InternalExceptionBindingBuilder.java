package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.ExceptionBindingBuilder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalExceptionBindingBuilder<T extends Throwable> implements ExceptionBindingBuilder<T> {

  private final InternalCommandBindingNode node;
  private final Class<T> exceptionType;

  private boolean ignoreInheritance = false;

  InternalExceptionBindingBuilder(InternalCommandBindingNode node, Class<T> exceptionType) {
    this.node = node;
    this.exceptionType = exceptionType;
  }

  @Override
  public ExceptionBindingBuilder<T> ignoringInheritance() {
    ignoreInheritance = true;
    return this;
  }

  @Override
  public CommandBindingNode with(Class<? extends ExceptionHandler<T>> clazz) {
    node.getTree().addExceptionBinding(exceptionType, clazz, ignoreInheritance);
    node.getMultibinder().handlerBinder().addBinding().to(clazz);
    return node;
  }

  @SuppressWarnings("unchecked")
  @Override
  public CommandBindingNode with(ExceptionHandler<T> handler) {
    node.getTree().addExceptionBinding(exceptionType,
        (Class<? extends ExceptionHandler<T>>) handler.getClass(), ignoreInheritance);
    node.getMultibinder().handlerBinder().addBinding().toInstance(handler);
    return node;
  }
}
