package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.bind.tree.CommandGroup;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CompilableGroup implements CommandGroup {

  private static final Object COMMAND_PLACEHOLDER = new Object();

  private final CompilableGroup parent;
  private final Set<CompilableGroup> children;
  private final Set<String> names;
  private final Set<CommandCallable> commands;

  private final InjectableGroupBindings groupBindings;
  private final Map<Class<?>, Object> compilables;

  CompilableGroup() {
    this(null, Set.of());
  }

  private CompilableGroup(@Nullable CompilableGroup parent, @Nonnull Set<String> names) {
    this.parent = parent;
    this.names = Set.copyOf(names);
    this.children = new HashSet<>();
    this.commands = new HashSet<>();
    this.compilables = new HashMap<>();
    this.groupBindings = parent != null
        ? InjectableGroupBindings.childOf(parent.groupBindings)
        : InjectableGroupBindings.create();
  }

  public void assimilate(CompilableGroup other) {
    try {
      assimilate(this, other);
    } catch (IllegalArgumentException e) {
      throw new BindingConflictException("Two different groups use one or more of the same names");
    }
  }

  private void assimilate(CompilableGroup own, CompilableGroup other) {
    own.groupBindings.assimilate(other.groupBindings);
    own.compilables.putAll(other.compilables);
    for (CompilableGroup child : other.children) {
      CompilableGroup thisChild = this.createChildIfNotExists(child.names);
      assimilate(thisChild, child);
    }
  }

  @Nullable
  @Override
  public CommandGroup getParent() {
    return parent;
  }

  @Nonnull
  @Override
  public Set<CommandGroup> getChildren() {
    return Collections.unmodifiableSet(children);
  }

  @Nonnull
  @Override
  public Set<String> getNames() {
    return names;
  }

  @Nonnull
  @Override
  public Set<CommandCallable> getCommands() {
    return Collections.unmodifiableSet(commands);
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

  @Inject
  void inject(@Commands Set<Object> commands, @Interceptors Set<Interceptor<?>> interceptors,
              @Handlers Set<ExceptionHandler<?>> handlers, @Mappers Set<ArgumentMapper<?>> mappers) {
    recursiveInject(this, commands, interceptors, handlers, mappers);
  }

  private void recursiveInject(CompilableGroup current,
                               Set<Object> commands,
                               Set<Interceptor<?>> interceptors,
                               Set<ExceptionHandler<?>> handlers,
                               Set<ArgumentMapper<?>> mappers) {

    current.groupBindings.inject(interceptors, handlers, mappers);
    for (Object command : commands) {
      current.compilables.computeIfPresent(command.getClass(), (k, v) -> command);
    }

    for (CompilableGroup child : current.children) {
      recursiveInject(child, commands, interceptors, handlers, mappers);
    }
  }

  public void compile(CommandCompiler compiler) {
    for (Object command : compilables.values()) {
      Set<CommandCallable> callables = compiler.compile(command, groupBindings);
      commands.addAll(callables);
    }
  }

  CompilableGroup createChildIfNotExists(Set<String> names) {
    for (CompilableGroup child : children) {
      if (child.names.equals(names)) {
        return child;
      }
    }

    checkArgument(
        children.stream().map(CommandGroup::getNames).noneMatch(
            (n) -> n.stream().anyMatch(names::contains)
        ),"Duplicate group name"
    );
    CompilableGroup child = new CompilableGroup(this, names);
    children.add(child);
    return child;
  }
}
