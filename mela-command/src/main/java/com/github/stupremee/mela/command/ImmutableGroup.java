package com.github.stupremee.mela.command;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.Sets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ImmutableGroup implements CommandGroup {

  private final ImmutableGroup parent;
  private final Set<String> names;
  private final Set<CommandCallable> commands;

  private Set<CommandGroup> children;

  private ImmutableGroup(ImmutableGroup parent, Set<String> names, Set<? extends CommandCallable> commands) {
    this.parent = parent;
    this.names = Set.copyOf(names);
    this.commands = Set.copyOf(commands);
  }

  @Nullable
  @Override
  public CommandGroup getParent() {
    return parent;
  }

  @Nonnull
  @Override
  public Set<CommandGroup> getChildren() {
    return children;
  }

  @Nonnull
  @Override
  public Set<String> getNames() {
    return names;
  }

  @Nonnull
  @Override
  public Set<CommandCallable> getCommands() {
    return commands;
  }

  @Override
  public String toString() {
    StringBuilder pathBuilder = new StringBuilder();
    ImmutableGroup group = this;
    while (group != null) {
      pathBuilder.insert(0, group.names);
      if (group.parent != null)
        pathBuilder.insert(0, " - ");
      group = group.parent;
    }
    return pathBuilder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ImmutableGroup that = (ImmutableGroup) o;
    return Objects.equals(names, that.names) &&
        Objects.equals(commands, that.commands) &&
        Objects.equals(children, that.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(names, commands, children);
  }

  private void setChildren(Set<ImmutableGroup> children) {
    checkState(this.children == null);
    this.children = Set.copyOf(children);
  }

  public static CommandGroup copyOf(CommandGroup group) {
    return of(group, GroupAccumulator.of(group));
  }

  public static <T> CommandGroup of(T root, GroupAccumulator<T> accumulator) {
    ImmutableGroup group = new ImmutableGroup(null, accumulator.getNames(root),
        accumulator.getCommands(root));
    group.setChildren(deepChildrenCopy(root, accumulator, group));
    return group;
  }

  private static <T> Set<ImmutableGroup> deepChildrenCopy(T template, GroupAccumulator<T> accumulator,
                                                          ImmutableGroup current) {
    Set<ImmutableGroup> children = Sets.newHashSet();
    for (T child : accumulator.getChildren(template)) {
      ImmutableGroup next = new ImmutableGroup(current, accumulator.getNames(child),
          accumulator.getCommands(child));
      next.setChildren(deepChildrenCopy(child, accumulator, next));
      children.add(next);
    }
    return children;
  }
}
