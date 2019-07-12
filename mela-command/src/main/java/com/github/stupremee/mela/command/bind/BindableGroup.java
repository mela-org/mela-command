package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.CompilableGroup;
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
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class BindableGroup implements CompilableGroup {

  private static final Object COMMAND_PLACEHOLDER = new Object();

  private final BindableGroup parent;
  private final Set<BindableGroup> children;
  private final Set<String> names;
  private final Set<CommandCallable> commands;

  private final InjectableGroupBindings groupBindings;
  private final Map<Class<?>, Object> compilables;

  public BindableGroup() {
    this(null, Set.of());
  }

  private BindableGroup(@Nullable BindableGroup parent, @Nonnull Set<String> names) {
    this.parent = parent;
    this.names = Set.copyOf(names);
    this.children = new HashSet<>();
    this.commands = new HashSet<>();
    this.compilables = new HashMap<>();
    this.groupBindings = parent != null
        ? InjectableGroupBindings.childOf(parent.groupBindings)
        : InjectableGroupBindings.create();
  }

  @Override
  public void assimilate(@Nonnull CompilableGroup other) {
    checkArgument(checkNotNull(other) instanceof BindableGroup,
        "Group to assimilate must be of the same type as this group");
    BindableGroup root = (BindableGroup) other;
    try {
      assimilate(this, root);
    } catch (IllegalArgumentException e) {
      throw new BindingConflictException("Two different groups use one or more of the same names");
    }
  }

  private void assimilate(BindableGroup own, BindableGroup other) {
    own.groupBindings.assimilate(other.groupBindings);
    own.compilables.putAll(other.compilables);
    for (BindableGroup child : other.children) {
      BindableGroup thisChild = this.createChildIfNotExists(child.names);
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

  public void addCommand(Class<?> commandClass) {
    compilables.put(commandClass, COMMAND_PLACEHOLDER);
  }

  public <T extends Annotation> void addInterceptorBinding(Class<T> annotationType,
                                                    Class<? extends Interceptor<T>> clazz) {
    groupBindings.putInterceptor(annotationType, clazz);
  }

  public <T extends Throwable> void addExceptionBinding(Class<T> exceptionType,
                                                 Class<? extends ExceptionHandler<T>> clazz) {
    groupBindings.putHandler(exceptionType, clazz);
  }

  public <T> void addParameterBinding(Object placeholder, Class<? extends ArgumentMapper<T>> clazz) {
    groupBindings.putMapper(placeholder, clazz);
  }

  @Inject
  void inject(@Commands Set<Object> commands, @Interceptors Set<Interceptor<?>> interceptors,
              @Handlers Set<ExceptionHandler<?>> handlers, @Mappers Set<ArgumentMapper<?>> mappers) {
    recursiveInject(this, commands, interceptors, handlers, mappers);
  }

  private void recursiveInject(BindableGroup current,
                               Set<Object> commands,
                               Set<Interceptor<?>> interceptors,
                               Set<ExceptionHandler<?>> handlers,
                               Set<ArgumentMapper<?>> mappers) {

    current.groupBindings.inject(interceptors, handlers, mappers);
    for (Object command : commands) {
      current.compilables.computeIfPresent(command.getClass(), (k, v) -> command);
    }

    for (BindableGroup child : current.children) {
      recursiveInject(child, commands, interceptors, handlers, mappers);
    }
  }

  @Override
  public void compile(@Nonnull CommandCompiler compiler) {
    checkNotNull(compiler);
    for (Object command : compilables.values()) {
      Set<CommandCallable> callables = compiler.compile(command, groupBindings);
      commands.addAll(callables);
    }
  }

  public BindableGroup createChildIfNotExists(@Nonnull Set<String> names) {
    for (BindableGroup child : children) {
      if (child.names.equals(names)) {
        return child;
      }
    }

    checkArgument(
        children.stream().map(CommandGroup::getNames).noneMatch(
            (n) -> n.stream().anyMatch(names::contains)
        ),"Duplicate group name"
    );
    BindableGroup child = new BindableGroup(this, names);
    children.add(child);
    return child;
  }
}
