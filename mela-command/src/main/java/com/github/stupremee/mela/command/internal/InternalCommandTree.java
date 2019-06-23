package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.binding.ExceptionBindings;
import com.github.stupremee.mela.command.binding.InterceptorBindings;
import com.github.stupremee.mela.command.binding.ParameterBindings;
import com.github.stupremee.mela.command.compile.CommandTree;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalCommandTree implements CommandTree {

  private final Group root;
  private Group current;

  InternalCommandTree(Group root) {
    this.root = root;
    this.current = root;
  }

  @Override
  public Group getCurrent() {
    return current;
  }

  @Override
  public void stepUp() {
    checkState(!isAtRoot(), "Current group is root group, can't step up");
    current = current.getParent();
  }

  @Override
  public void stepToRoot() {
    while (!isAtRoot())
      stepUp();
  }

  @Override
  public void stepDown(int childIndex) {
    current = current.getChildren().get(childIndex);
  }

  @Override
  public void stepDown(Group child) {
    checkArgument(current.getChildren().contains(child),
        "Provided group is not a child of current group");
    current = child;
  }

  @Override
  public boolean isAtRoot() {
    return current == root;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    InternalCommandTree that = (InternalCommandTree) o;
    return Objects.equals(root, that.root);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(root);
  }

  static final class InternalGroup implements Group {

    private final Group parent;
    private List<Group> children;
    private final List<String> aliases;
    private final ParameterBindings parameterBindings;
    private final InterceptorBindings interceptorBindings;
    private final ExceptionBindings exceptionBindings;
    private final Collection<?> commandObjects;

    InternalGroup(Group parent, Set<String> aliases,
                  ParameterBindings parameterBindings, InterceptorBindings interceptorBindings,
                  ExceptionBindings exceptionBindings, Collection<?> commandObjects) {
      this.parent = parent;
      this.aliases = List.copyOf(aliases);
      this.parameterBindings = parameterBindings;
      this.interceptorBindings = interceptorBindings;
      this.exceptionBindings = exceptionBindings;
      this.commandObjects = Collections.unmodifiableCollection(commandObjects);
      this.children = Lists.newArrayList();
    }

    @Override
    public Group getParent() {
      return parent;
    }

    @Override
    public List<Group> getChildren() {
      return children;
    }

    @Override
    public List<String> getAliases() {
      return aliases;
    }

    @Override
    public ParameterBindings getParameterBindings() {
      return parameterBindings;
    }

    @Override
    public InterceptorBindings getInterceptorBindings() {
      return interceptorBindings;
    }

    @Override
    public ExceptionBindings getExceptionBindings() {
      return exceptionBindings;
    }

    @Override
    public Collection<?> getCommandObjects() {
      return commandObjects;
    }

    void addChild(Group group) {
      children.add(group);
    }

    void makeImmutable() {
      children = List.copyOf(children);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      InternalGroup group = (InternalGroup) o;
      return Objects.equals(parent, group.parent) &&
          Objects.equals(children, group.children) &&
          Objects.equals(aliases, group.aliases) &&
          Objects.equals(parameterBindings, group.parameterBindings) &&
          Objects.equals(interceptorBindings, group.interceptorBindings) &&
          Objects.equals(exceptionBindings, group.exceptionBindings) &&
          Objects.equals(commandObjects, group.commandObjects);
    }

    @Override
    public int hashCode() {
      return Objects.hash(parent, children, aliases, parameterBindings, interceptorBindings,
          exceptionBindings, commandObjects);
    }

    @Override
    public String toString() {
      return parent == null ? aliases.toString() : parent.toString() + " - " + aliases.toString();
    }
  }
}
