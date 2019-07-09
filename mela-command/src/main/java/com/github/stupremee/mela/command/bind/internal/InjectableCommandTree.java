package com.github.stupremee.mela.command.bind.internal;

import com.github.stupremee.mela.command.bind.tree.Group;
import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.bind.ExceptionBindings;
import com.github.stupremee.mela.command.bind.InterceptorBindings;
import com.github.stupremee.mela.command.bind.ParameterBindings;
import com.github.stupremee.mela.command.bind.tree.CommandTree;
import com.github.stupremee.mela.command.bind.tree.RecursiveCommandTree;
import com.github.stupremee.mela.command.bind.BindingConflictException;
import com.github.stupremee.mela.command.inject.InjectionObjectHolder;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Key;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InjectableCommandTree extends RecursiveCommandTree<InjectableCommandTree.MutableGroup> {

  private static final Object COMMAND_PLACEHOLDER = new Object();

  InjectableCommandTree() {
    this(new MutableGroup(new InjectableParameterBindings(),
        new InjectableInterceptorBindings(), new InjectableExceptionBindings(),
        Collections.emptySet(), null));
  }

  private InjectableCommandTree(MutableGroup root) {
    super(root);
  }

  @Nonnull
  @Override
  public CommandTree merge(@Nonnull CommandTree o) {
    checkArgument(checkNotNull(o) instanceof InjectableCommandTree,
        "Must be instance of RecursiveCommandTree to merge");
    InjectableCommandTree other = (InjectableCommandTree) o;
    InjectableCommandTree mergingTree = new InjectableCommandTree();
    mergingTree.mergeMutating(this, other);
    return mergingTree;
  }

  private void mergeMutating(InjectableCommandTree one, InjectableCommandTree two) {
    this.stepToRoot();
    one.stepToRoot();
    this.mergeMutating(one);
    one.stepToRoot();
    this.stepToRoot();
    two.stepToRoot();
    this.mergeMutating(two);
    two.stepToRoot();
    this.stepToRoot();
  }

  private void mergeMutating(InjectableCommandTree tree) {
    currentNode.interceptorBindings.putAll(tree.currentNode.interceptorBindings);
    currentNode.parameterBindings.putAll(tree.currentNode.parameterBindings);
    currentNode.exceptionBindings.putAll(tree.currentNode.exceptionBindings);
    currentNode.commands.putAll(tree.currentNode.commands);
    for (MutableGroup child : tree.currentNode.children) {
      tree.stepDown(child);
      try {
        this.stepDown(this.createChildIfNotExists(child.aliases));
      } catch (IllegalArgumentException e) {
        throw new BindingConflictException("Two groups from two different CommandBinders that " +
            "are on the same layer have the same alias", e);
      }
      mergeMutating(tree);
    }
  }

  @Inject
  public void inject(InjectionObjectHolder holder) {
    this.stepToRoot();
    this.recursiveInject(holder);
  }

  private void recursiveInject(InjectionObjectHolder holder) {
    for (Object command : holder.getCommandObjects())
      currentNode.commands.computeIfPresent(command.getClass(), (k, v) -> command);
    currentNode.parameterBindings.inject(holder.getMappers());
    currentNode.interceptorBindings.inject(holder.getInterceptors());
    currentNode.exceptionBindings.inject(holder.getHandlers());
    for (MutableGroup child : currentNode.children) {
      this.stepDown(child);
      recursiveInject(holder);
    }
    currentNode.makeImmutable();
  }

  Group createChildIfNotExists(Set<String> aliases) {
    Group existingChild = currentNode.getChild(aliases);
    return existingChild != null
        ? existingChild
        : currentNode.addChild(aliases);
  }

  void addCommand(Class<?> commandClass) {
    currentNode.commands.put(commandClass, COMMAND_PLACEHOLDER);
  }

  <T> void addParameterBinding(Key<T> key,
                               Class<? extends ArgumentMapper<T>> mapperType) {
    currentNode.parameterBindings.put(key, mapperType);
  }

  <T extends Annotation> void addInterceptorBinding(Class<T> annotationType,
                                                    Class<? extends Interceptor<T>> interceptorType) {
    currentNode.interceptorBindings.put(annotationType, interceptorType);
  }

  <T extends Throwable> void addExceptionBinding(Class<T> exceptionType,
                                                 Class<? extends ExceptionHandler<T>> handlerType,
                                                 boolean ignoreInheritance) {
    currentNode.exceptionBindings.put(exceptionType, handlerType, ignoreInheritance);
  }

  static final class MutableGroup implements Group {

    private final MutableGroup parent;
    private final InjectableParameterBindings parameterBindings;
    private final InjectableInterceptorBindings interceptorBindings;
    private final InjectableExceptionBindings exceptionBindings;
    private final Set<String> aliases;
    private final Set<MutableGroup> children;

    private Map<Class<?>, Object> commands;
    private Set<Group> finalChildren;


    private MutableGroup(InjectableParameterBindings parameterBindings,
                         InjectableInterceptorBindings interceptorBindings,
                         InjectableExceptionBindings exceptionBindings, Set<String> aliases,
                         MutableGroup parent) {
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
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      MutableGroup that = (MutableGroup) o;
      return Objects.equals(parameterBindings, that.parameterBindings) &&
          Objects.equals(interceptorBindings, that.interceptorBindings) &&
          Objects.equals(exceptionBindings, that.exceptionBindings) &&
          Objects.equals(aliases, that.aliases) &&
          Objects.equals(commands, that.commands) &&
          Objects.equals(children, that.children);
    }

    private Group addChild(Set<String> aliases) {
      checkForDuplicateChildAlias(aliases);
      MutableGroup child = new MutableGroup(parameterBindings.copy(), interceptorBindings.copy(),
          exceptionBindings.copy(), aliases, this);
      children.add(child);
      return child;
    }

    private void checkForDuplicateChildAlias(Set<String> aliases) {
      for (MutableGroup element : children) {
        for (String alias : element.aliases) {
          checkArgument(!aliases.contains(alias),
              "Duplicate alias for different groups in the same layer: " + alias);
        }
      }
    }

    private Group getChild(Set<String> aliases) {
      for (MutableGroup element : children) {
        if (element.aliases.equals(aliases))
          return element;
      }
      return null;
    }

    @Override
    public int hashCode() {
      return Objects.hash(parameterBindings, interceptorBindings, exceptionBindings,
          aliases, commands, children);
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      for (MutableGroup current = this; current != null; current = current.parent) {
        builder.insert(0, current.aliases);
        if (current.parent != null)
          builder.insert(0, " - ");
      }
      return builder.toString();
    }

    @Override
    public Group getParent() {
      return parent;
    }

    @Nonnull
    @Override
    public Set<Group> getChildren() {
      return finalChildren == null ? Set.copyOf(children) : finalChildren;
    }

    @Nonnull
    @Override
    public Set<String> getNames() {
      return aliases;
    }

    @Nonnull
    @Override
    public ParameterBindings getParameterBindings() {
      return parameterBindings;
    }

    @Nonnull
    @Override
    public InterceptorBindings getInterceptorBindings() {
      return interceptorBindings;
    }

    @Nonnull
    @Override
    public ExceptionBindings getExceptionBindings() {
      return exceptionBindings;
    }

    @Nonnull
    @Override
    public Collection<?> getCommandObjects() {
      return commands.values();
    }

    private void makeImmutable() {
      finalChildren = Set.copyOf(children);
      commands = Map.copyOf(commands);
    }
  }
}
