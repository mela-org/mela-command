package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.github.stupremee.mela.command.Interceptor;
import com.github.stupremee.mela.command.binding.ParameterBindings;
import com.github.stupremee.mela.command.compile.CommandTree;
import com.github.stupremee.mela.command.compile.InjectableCommandTree;
import com.github.stupremee.mela.command.exception.ConflictException;
import com.github.stupremee.mela.command.inject.InjectionObjectHolder;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 23.06.2019 tests
final class InternalInjectableCommandTree implements InjectableCommandTree {

  private InjectableGroup node;

  InternalInjectableCommandTree() {
    this.node = new InjectableGroup(new InternalParameterBindings(),
        new InternalInterceptorBindings(), new InternalExceptionBindings(),
        Collections.emptySet(), null);
  }

  @Override
  public InjectableCommandTree merge(InjectableCommandTree o) {
    checkArgument(o instanceof InternalInjectableCommandTree,
        "Must be instance of InternalInjectableCommandTree to merge");
    InternalInjectableCommandTree other = (InternalInjectableCommandTree) o;
    this.stepToRoot();
    other.stepToRoot();
    InternalInjectableCommandTree mergingTree = new InternalInjectableCommandTree();
    mergingTree.mutatingMerge(this, other);
    return mergingTree;
  }

  private void mutatingMerge(InternalInjectableCommandTree one, InternalInjectableCommandTree two) {
    mutatingMerge(one);
    one.stepToRoot();
    this.stepToRoot();
    mutatingMerge(two);
    two.stepToRoot();
    this.stepToRoot();
  }

  private void mutatingMerge(InternalInjectableCommandTree tree) {
    node.interceptorBindings.putAll(tree.node.interceptorBindings);
    node.parameterBindings.putAll(tree.node.parameterBindings);
    node.exceptionBindings.putAll(tree.node.exceptionBindings);
    node.commands.putAll(tree.node.commands);
    for (InjectableGroup child : tree.node.children) {
      tree.node = child;
      try {
        this.stepDown(child.aliases);
      } catch (IllegalArgumentException e) {
        throw new ConflictException("Two groups from two different CommandBinders that " +
            "are on the same layer have the same alias", e);
      }
      mutatingMerge(tree);
    }
  }

  @Override
  public CommandTree inject(InjectionObjectHolder holder) {
    this.stepToRoot();
    this.recursiveInject(node, holder);
  }

  private void recursiveInject(InjectableGroup group, InjectionObjectHolder holder) {
    for (Object command : holder.getCommandObjects())
      group.commands.computeIfPresent(command.getClass(), (k, v) -> command);
    group.parameterBindings.inject(holder.getMappers());
    group.interceptorBindings.inject(holder.getInterceptors());
    group.exceptionBindings.inject(holder.getHandlers());
    for (InjectableGroup child : group.children)
      recursiveInject(child, holder);
  }

  void stepDown(Set<String> childAliases) {
    InjectableGroup child = createChild(childAliases);
    checkNodeForDuplicateChildAliases(child);
    boolean exists = node.children.add(child);
    this.node = !exists ? child : getExistingChildReference(child);
  }

  private InjectableGroup createChild(Set<String> aliases) {
    return new InjectableGroup(node.parameterBindings.copy(),
        node.interceptorBindings.copy(), node.exceptionBindings.copy(), aliases, node);
  }

  private void checkNodeForDuplicateChildAliases(InjectableGroup child) {
    for (InjectableGroup element : node.children) {
      if (!element.equals(child))
        continue;
      for (String alias : element.aliases) {
        checkArgument(!child.aliases.contains(alias),
            "Duplicate alias for different groups in the same layer: " + alias);
      }
    }
  }

  private InjectableGroup getExistingChildReference(InjectableGroup child) {
    for (InjectableGroup element : node.children) {
      if (element.equals(child))
        return element;
    }
    return null;
  }

  void stepUp() {
    checkState(!isRoot(), "Current node is root node, can't step up");
    node = node.parent;
  }

  void stepToRoot() {
    while (!isRoot())
      stepUp();
  }

  boolean isRoot() {
    return node.parent == null;
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
    final Map<Class<?>, Object> commands;
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
