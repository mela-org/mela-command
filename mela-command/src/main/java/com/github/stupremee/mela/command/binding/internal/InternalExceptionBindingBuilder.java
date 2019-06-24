package com.github.stupremee.mela.command.binding.internal;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.ExceptionBindingBuilder;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalExceptionBindingBuilder<T extends Throwable> implements ExceptionBindingBuilder<T> {

  private final InternalCommandBindingNode node;
  private final InjectableRecursiveCommandTree tree;
  private final Multibinder<ExceptionHandler<?>> binder;
  private final Class<T> exceptionType;

  private boolean ignoreInheritance = false;

  InternalExceptionBindingBuilder(InternalCommandBindingNode node, InjectableRecursiveCommandTree tree,
                                  Multibinder<ExceptionHandler<?>> binder, Class<T> exceptionType) {
    this.node = node;
    this.tree = tree;
    this.binder = binder;
    this.exceptionType = exceptionType;
  }

  @Nonnull
  @Override
  public ExceptionBindingBuilder<T> ignoringInheritance() {
    ignoreInheritance = true;
    return this;
  }

  @Nonnull
  @Override
  public CommandBindingNode with(@Nonnull Class<? extends ExceptionHandler<T>> clazz) {
    checkNotNull(clazz);
    tree.addExceptionBinding(exceptionType, clazz, ignoreInheritance);
    binder.addBinding().to(clazz);
    return node;
  }

  @Nonnull
  @SuppressWarnings("unchecked")
  @Override
  public CommandBindingNode with(@Nonnull ExceptionHandler<T> handler) {
    checkNotNull(handler);
    tree.addExceptionBinding(exceptionType,
        (Class<? extends ExceptionHandler<T>>) handler.getClass(), ignoreInheritance);
    binder.addBinding().toInstance(handler);
    return node;
  }
}
