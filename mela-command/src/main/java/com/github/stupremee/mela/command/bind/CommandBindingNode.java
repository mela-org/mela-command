package com.github.stupremee.mela.command.bind;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandBindingNode {

  private final CommandBindingNode parent;
  private final CommandMultibinder multibinder;
  private CompilableGroup group;

  CommandBindingNode(CommandMultibinder multibinder) {
    this.parent = null;
    this.multibinder = multibinder;
    this.group = new CompilableGroup();
    Multibinder<CompilableGroup> groupBinder =
        Multibinder.newSetBinder(this.multibinder.binder(), CompilableGroup.class);
    groupBinder.addBinding().toInstance(group);
  }

  private CommandBindingNode(CommandBindingNode parent, CompilableGroup root) {
    this.parent = parent;
    this.multibinder = parent.multibinder;
    this.group = root;
  }

  @Nonnull
  public CommandBindingNode group(@Nonnull String... aliases) {
    checkNotNull(aliases);
    CompilableGroup child = group.createChildIfNotExists(Set.of(aliases));
    return new CommandBindingNode(this, child);
  }


  public CommandBindingNode parent() {
    checkState(parent != null, "Cannot go to parent, this is the highest node");
    return parent;
  }

  @Nonnull
  public CommandBindingNode add(@Nonnull Class<?> commandClass) {
    checkNotNull(commandClass);
    group.addCommand(commandClass);
    multibinder.commandObjectBinder().addBinding().to(commandClass);
    return this;
  }

  @Nonnull
  public CommandBindingNode add(@Nonnull Object command) {
    checkNotNull(command);
    group.addCommand(command.getClass());
    multibinder.commandObjectBinder().addBinding().toInstance(command);
    return this;
  }

  @Nonnull
  public <T extends Annotation> InterceptorBindingBuilder<T> interceptAt(@Nonnull Class<T> annotationType) {
    checkNotNull(annotationType);
    return new InterceptorBindingBuilder<>(this, group, multibinder.interceptorBinder(), annotationType);
  }

  @Nonnull
  public <T extends Throwable> ExceptionBindingBuilder<T> handle(@Nonnull Class<T> exceptionType) {
    checkNotNull(exceptionType);
    return new ExceptionBindingBuilder<>(this, group, multibinder.handlerBinder(), exceptionType);
  }

  @Nonnull
  public <T> ParameterBindingBuilder<T> bindParameter(@Nonnull Class<T> parameterType) {
    return bindParameter(TypeLiteral.get(parameterType));
  }

  @Nonnull
  public <T> ParameterBindingBuilder<T> bindParameter(@Nonnull TypeLiteral<T> literal) {
    checkNotNull(literal);
    return new ParameterBindingBuilder<>(this, group, multibinder.mapperBinder(), literal);
  }

}
