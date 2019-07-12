package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.GroupBindings;
import com.github.stupremee.mela.command.compile.CompilableGroup;
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
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InjectableGroup implements CompilableGroup {

  private static final Object COMMAND_PLACEHOLDER = new Object();

  private final InjectableGroup parent;
  private final Set<InjectableGroup> children;
  private final Set<String> names;
  private final InjectableGroupBindings groupBindings;
  private final Map<Class<?>, Object> compilables;

  InjectableGroup() {
    this(null, Set.of());
  }

  private InjectableGroup(@Nullable InjectableGroup parent, @Nonnull Set<String> names) {
    this.parent = parent;
    this.names = Set.copyOf(names);
    this.children = new HashSet<>();
    this.compilables = new HashMap<>();
    this.groupBindings = parent == null
        ? InjectableGroupBindings.create()
        : InjectableGroupBindings.childOf(parent.groupBindings);
  }

  @Nonnull
  @Override
  public CompilableGroup merge(@Nonnull CompilableGroup other) {
    checkArgument(checkNotNull(other) instanceof InjectableGroup,
        "Group to assimilate must be of the same type as this group");
    InjectableGroup root = (InjectableGroup) other;
    InjectableGroup copy = this.copy();
    try {
      assimilate(copy, root);
    } catch (IllegalArgumentException e) {
      throw new BindingConflictException("Two different groups use one or more of the same names");
    }
    return copy;
  }

  private InjectableGroup copy() {
    InjectableGroup copy = new InjectableGroup();
    this.assimilate(copy, this);
    return copy;
  }

  private void assimilate(InjectableGroup own, InjectableGroup other) {
    own.groupBindings.assimilate(other.groupBindings);
    own.compilables.putAll(other.compilables);
    for (InjectableGroup otherChild : other.children) {
      InjectableGroup ownChild = own.createChildIfNotExists(otherChild.names);
      assimilate(ownChild, otherChild);
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
  public GroupBindings getBindings() {
    return groupBindings;
  }

  @Nonnull
  @Override
  public Collection<?> getUncompiledCommands() {
    return Collections.unmodifiableCollection(compilables.values());
  }

  @Inject
  void inject(@Commands Set<Object> commands, @Interceptors Set<Interceptor<?>> interceptors,
              @Handlers Set<ExceptionHandler<?>> handlers, @Mappers Set<ArgumentMapper<?>> mappers) {
    recursiveInject(this, commands, interceptors, handlers, mappers);
  }

  private void recursiveInject(InjectableGroup current,
                               Set<Object> commands,
                               Set<Interceptor<?>> interceptors,
                               Set<ExceptionHandler<?>> handlers,
                               Set<ArgumentMapper<?>> mappers) {

    current.groupBindings.inject(interceptors, handlers, mappers);
    for (Object command : commands) {
      current.compilables.computeIfPresent(command.getClass(), (k, v) -> command);
    }

    for (InjectableGroup child : current.children) {
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

  InjectableGroup createChildIfNotExists(@Nonnull Set<String> names) {
    for (InjectableGroup child : children) {
      if (child.names.equals(names)) {
        return child;
      }
    }

    checkArgument(
        children.stream().map((group) -> group.names).noneMatch(
            (n) -> n.stream().anyMatch(names::contains)
        ),"Duplicate group name"
    );
    InjectableGroup child = new InjectableGroup(this, names);
    children.add(child);
    return child;
  }

}
