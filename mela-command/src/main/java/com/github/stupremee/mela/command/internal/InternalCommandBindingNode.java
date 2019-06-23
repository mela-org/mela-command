package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.github.stupremee.mela.command.binding.ExceptionBindingBuilder;
import com.github.stupremee.mela.command.binding.InterceptorBindingBuilder;
import com.github.stupremee.mela.command.binding.ParameterBindingBuilder;
import com.google.inject.TypeLiteral;

import java.lang.annotation.Annotation;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalCommandBindingNode implements CommandBindingNode {

  private final InternalCommandBindingNode parent;
  private final CommandMultibinder multibinder;
  private final InternalInjectableCommandTree tree;

  InternalCommandBindingNode(CommandMultibinder multibinder) {
    this.parent = null;
    this.multibinder = multibinder;
    this.tree = new InternalInjectableCommandTree();
  }

  private InternalCommandBindingNode(InternalCommandBindingNode parent) {
    this.parent = parent;
    this.multibinder = parent.multibinder;
    this.tree = parent.tree;
  }

  @Override
  public CommandBindingNode group(String... aliases) {
    tree.stepDown(Set.of(aliases));
    return new InternalCommandBindingNode(this);
  }

  @Override
  public CommandBindingNode parent() {
    checkState(parent != null, "Cannot go to parent, this is the highest node");
    return parent;
  }

  @Override
  public CommandBindingNode bind(Class<?> commandClass) {
    tree.addCommand(commandClass);
    multibinder.addCommand(commandClass);
    return this;
  }

  @Override
  public CommandBindingNode bind(Object command) {
    tree.addCommand(command.getClass());
    multibinder.addCommand(command);
    return this;
  }

  @Override
  public <T extends Annotation> InterceptorBindingBuilder<T> interceptAt(Class<T> annotationType) {
    return new InternalInterceptorBindingBuilder<>(annotationType);
  }

  @Override
  public <T extends Throwable> ExceptionBindingBuilder<T> handle(Class<T> exceptionType) {
    return new InternalExceptionBindingBuilder<>(exceptionType);
  }

  // TODO: 23.06.2019 Parameter keys
  @Override
  public <T> ParameterBindingBuilder<T> bindParameter(Class<T> parameterType) {

  }

  @Override
  public <T> ParameterBindingBuilder<T> bindParameter(TypeLiteral<T> literal) {

  }

  private final class InternalInterceptorBindingBuilder<T extends Annotation> implements InterceptorBindingBuilder<T> {

    final Class<T> annotationType;

    InternalInterceptorBindingBuilder(Class<T> annotationType) {
      this.annotationType = annotationType;
    }

    @Override
    public CommandBindingNode with(Class<? extends Interceptor<T>> clazz) {
      tree.addInterceptorBinding(annotationType, clazz);
      multibinder.addInterceptor(clazz);
      return InternalCommandBindingNode.this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CommandBindingNode with(Interceptor<T> interceptor) {
      tree.addInterceptorBinding(annotationType, (Class<? extends Interceptor<T>>) interceptor.getClass());
      multibinder.addInterceptor(interceptor);
      return InternalCommandBindingNode.this;
    }
  }

  private final class InternalExceptionBindingBuilder<T extends Throwable> implements ExceptionBindingBuilder<T> {

    final Class<T> exceptionType;

    boolean ignoreInheritance = false;

    InternalExceptionBindingBuilder(Class<T> exceptionType) {
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
      multibinder.addHandler(clazz);
      return InternalCommandBindingNode.this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CommandBindingNode with(ExceptionHandler<T> handler) {
      tree.addExceptionBinding(exceptionType,
          (Class<? extends ExceptionHandler<T>>) handler.getClass(), ignoreInheritance);
      multibinder.addHandler(handler);
      return InternalCommandBindingNode.this;
    }
  }
}
