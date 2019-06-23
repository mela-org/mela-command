package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.binding.ParameterBindings;
import com.github.stupremee.mela.command.compile.CommandTree;
import com.github.stupremee.mela.command.compile.InjectableCommandTree;
import com.github.stupremee.mela.command.inject.InjectionObjectHolder;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.annotation.Annotation;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class InternalInjectableCommandTree implements InjectableCommandTree {

  private InjectableGroup node = InjectableGroup.root();

  @Override
  public InjectableCommandTree merge(InjectableCommandTree other) {
    return null;
  }

  @Override
  public CommandTree inject(InjectionObjectHolder holder) {
    return null;
  }

  void stepToNewChild(List<String> aliases) {
    this.node = node.child(Sets.newLinkedHashSet(aliases));
  }

  void stepUp() {
    checkState(node.parent != null, "Current node is root node, can't step up");
    node = node.parent;
  }

  void addCommand(Class<?> commandClass) {
    node.commands.put(commandClass, null);
  }

  <T> void addParameterBinding(ParameterBindings.Key<T> key,
                               Class<? extends ArgumentMapper<T>> mapperType) {
    node.parameterBindings.put(key, mapperType);
  }

  <T extends Annotation> void addInterceptorBinding(Class<T> annotationType,
                                                    Class<? extends Interceptor<T>> interceptorType) {
    node.interceptorBindings.put(annotationType, interceptorType);
  }

  <T extends Throwable> void addExceptionBinding(Class<T> exceptionType,
                                                 Class<? extends ExceptionHandler<T>> handlerType,
                                                 boolean ignoreInheritance) {
    node.exceptionBindings.put(exceptionType, handlerType, ignoreInheritance);
  }

  private static final class InjectableGroup {
    final InternalParameterBindings parameterBindings;
    final InternalInterceptorBindings interceptorBindings;
    final InternalExceptionBindings exceptionBindings;
    final Set<String> aliases;
    final Map<Class<?>, ?> commands;
    final Set<InjectableGroup> children;
    final InjectableGroup parent;

    InjectableGroup(InternalParameterBindings parameterBindings,
                            InternalInterceptorBindings interceptorBindings,
                            InternalExceptionBindings exceptionBindings, Set<String> aliases,
                    InjectableGroup parent) {
      this.parameterBindings = parameterBindings;
      this.interceptorBindings = interceptorBindings;
      this.exceptionBindings = exceptionBindings;
      this.commands = Maps.newHashMap();
      this.aliases = aliases;
      this.parent = parent;
      this.children = Sets.newHashSet();
    }

    InjectableGroup child(Set<String> aliases) {
      InjectableGroup child = new InjectableGroup(parameterBindings.copy(),
          interceptorBindings.copy(), exceptionBindings.copy(), aliases, this);
      checkArgument(children.stream()
          .filter((c) -> !c.equals(child))
          .map((c) -> c.aliases)
          .noneMatch((strings) -> strings.stream().anyMatch(aliases::contains)),
          "Different groups must not be described by same aliases");
      return children.add(child) ? child : children.stream()
          .filter(child::equals)
          .findFirst()
          .orElseThrow(AssertionError::new);
    }

    static InjectableGroup root() {
      return new InjectableGroup(new InternalParameterBindings(),
          new InternalInterceptorBindings(), new InternalExceptionBindings(),
          Collections.emptySet(), null);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      InjectableGroup that = (InjectableGroup) o;
      return Objects.equals(parameterBindings, that.parameterBindings) &&
          Objects.equals(interceptorBindings, that.interceptorBindings) &&
          Objects.equals(exceptionBindings, that.exceptionBindings) &&
          Objects.equals(aliases, that.aliases) &&
          Objects.equals(commands, that.commands) &&
          Objects.equals(children, that.children) &&
          Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
      return Objects.hash(parameterBindings, interceptorBindings, exceptionBindings, aliases, commands, children, parent);
    }
  }
}
