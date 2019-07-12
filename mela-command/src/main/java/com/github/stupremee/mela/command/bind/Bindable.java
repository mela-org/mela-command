package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.Compilable;
import com.github.stupremee.mela.command.ImmutableGroup;
import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.inject.Commands;
import com.github.stupremee.mela.command.inject.Handlers;
import com.github.stupremee.mela.command.inject.Interceptors;
import com.github.stupremee.mela.command.inject.Mappers;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class Bindable implements Compilable {

  private static final Object COMMAND_PLACEHOLDER = new Object();

  private final Set<Bindable> children;
  private final Set<String> names;
  private final Set<CommandCallable> commands;

  private final InjectableGroupBindings groupBindings;
  private final Map<Class<?>, Object> compilables;

  Bindable() {
    this(InjectableGroupBindings.create(), Set.of());
  }

  private Bindable(@Nonnull InjectableGroupBindings bindings, @Nonnull Set<String> names) {
    this.names = Set.copyOf(names);
    this.children = new HashSet<>();
    this.commands = new HashSet<>();
    this.compilables = new HashMap<>();
    this.groupBindings = bindings;
  }

  @Override
  public CommandGroup compile(@Nonnull CommandCompiler compiler) {
    checkNotNull(compiler);
    for (Object command : compilables.values()) {
      Set<CommandCallable> callables = compiler.compile(command, groupBindings);
      commands.addAll(callables);
    }
    return ImmutableGroup.copyOf(this);
  }

  @Override
  public Compilable assimilate(@Nonnull Compilable other) {
    checkArgument(checkNotNull(other) instanceof Bindable,
        "Group to assimilate must be of the same type as this group");
    Bindable root = (Bindable) other;
    Bindable copy = this.copy();
    try {
      assimilate(copy, root);
    } catch (IllegalArgumentException e) {
      throw new BindingConflictException("Two different groups use one or more of the same names");
    }
    return copy;
  }

  private Bindable copy() {
    Bindable copy = new Bindable();
    this.assimilate(copy, this);
    return copy;
  }

  private void assimilate(Bindable own, Bindable other) {
    own.groupBindings.assimilate(other.groupBindings);
    own.compilables.putAll(other.compilables);
    for (Bindable otherChild : other.children) {
      Bindable ownChild = own.createChildIfNotExists(otherChild.names);
      assimilate(ownChild, otherChild);
    }
  }

  @Inject
  void inject(@Commands Set<Object> commands, @Interceptors Set<Interceptor<?>> interceptors,
              @Handlers Set<ExceptionHandler<?>> handlers, @Mappers Set<ArgumentMapper<?>> mappers) {
    recursiveInject(this, commands, interceptors, handlers, mappers);
  }

  private void recursiveInject(Bindable current,
                               Set<Object> commands,
                               Set<Interceptor<?>> interceptors,
                               Set<ExceptionHandler<?>> handlers,
                               Set<ArgumentMapper<?>> mappers) {

    current.groupBindings.inject(interceptors, handlers, mappers);
    for (Object command : commands) {
      current.compilables.computeIfPresent(command.getClass(), (k, v) -> command);
    }

    for (Bindable child : current.children) {
      recursiveInject(child, commands, interceptors, handlers, mappers);
    }
  }

  void addCommand(Class<?> commandClass) {
    compilables.put(commandClass, COMMAND_PLACEHOLDER);
  }

  <T extends Annotation> void addInterceptorBinding(Class<T> annotationType,
                                                    Class<? extends Interceptor<T>> clazz) {
    groupBindings.putInterceptor(annotationType, clazz);
  }

  <T extends Throwable> void addExceptionBinding(Class<T> exceptionType,
                                                 Class<? extends ExceptionHandler<T>> clazz) {
    groupBindings.putHandler(exceptionType, clazz);
  }

  <T> void addParameterBinding(Object placeholder, Class<? extends ArgumentMapper<T>> clazz) {
    groupBindings.putMapper(placeholder, clazz);
  }

  Bindable createChildIfNotExists(@Nonnull Set<String> names) {
    for (Bindable child : children) {
      if (child.names.equals(names)) {
        return child;
      }
    }

    checkArgument(
        children.stream().map((group) -> group.names).noneMatch(
            (n) -> n.stream().anyMatch(names::contains)
        ),"Duplicate group name"
    );
    Bindable child = new Bindable(InjectableGroupBindings.childOf(this.groupBindings), names);
    children.add(child);
    return child;
  }
}
