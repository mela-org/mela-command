package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ImmutableGroup implements CommandGroup {

  public static final CommandGroup EMPTY = childlessRoot(ImmutableSet.of(), ImmutableSet.of());

  private final ImmutableGroup parent;
  private final Set<String> names;
  private final Set<CommandCallable> commands;

  private Set<CommandGroup> children;

  private ImmutableGroup(
      ImmutableGroup parent, Set<String> names, Set<? extends CommandCallable> commands) {
    this.parent = parent;
    this.names = ImmutableSet.copyOf(names);
    this.commands = ImmutableSet.copyOf(commands);
  }

  @CheckReturnValue
  @Nonnull
  public static ImmutableGroupBuilder builder() {
    return new ImmutableGroupBuilder();
  }

  @Nonnull
  public static CommandGroup childlessRoot(
      @Nonnull Set<String> names, @Nonnull Set<CommandCallable> commands) {
    ImmutableGroup group = new ImmutableGroup(null, names, commands);
    group.setChildren(ImmutableSet.of());
    return group;
  }

  @Nonnull
  public static CommandGroup copyOf(@Nonnull CommandGroup group) {
    return checkNotNull(group) instanceof ImmutableGroup
        ? group
        : of(group, GroupAssembler.forGroup());
  }

  @Nonnull
  public static <T> CommandGroup of(@Nonnull T root, @Nonnull GroupAssembler<T> accumulator) {
    checkNotNull(root);
    checkNotNull(accumulator);
    ImmutableGroup group = new ImmutableGroup(null, accumulator.getNames(root),
        accumulator.getCommands(root));
    group.setChildren(deepChildrenCopy(root, accumulator, group));
    return group;
  }

  private static <T> Set<ImmutableGroup> deepChildrenCopy(T template, GroupAssembler<T> accumulator,
                                                          ImmutableGroup current) {
    Set<ImmutableGroup> children = Sets.newHashSet();
    for (T child : accumulator.getChildren(template)) {
      Set<String> names = accumulator.getNames(child);
      checkForDuplicateNames(children, names);
      ImmutableGroup next = new ImmutableGroup(current, names, accumulator.getCommands(child));
      next.setChildren(deepChildrenCopy(child, accumulator, next));
      children.add(next);
    }
    return children;
  }

  private static void checkForDuplicateNames(
      Set<ImmutableGroup> children, Set<String> namesToCheck) {
    for (CommandGroup child : children) {
      for (String name : child.getNames()) {
        if (namesToCheck.contains(name)) {
          throw new IllegalArgumentException("Group "
              + namesToCheck + " has duplicate uses already bound name: " + name);
        }
      }
    }
  }

  @Nullable
  @Override
  public CommandGroup getParent() {
    return parent;
  }

  @Nonnull
  @Override
  public Set<? extends CommandGroup> getChildren() {
    return children;
  }

  private void setChildren(Set<ImmutableGroup> children) {
    checkState(this.children == null);
    this.children = ImmutableSet.copyOf(children);
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
      if (group.parent != null) {
        pathBuilder.insert(0, " - ");
      }
      group = group.parent;
    }
    return pathBuilder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImmutableGroup that = (ImmutableGroup) o;
    return Objects.equals(names, that.names)
        && Objects.equals(commands, that.commands)
        && Objects.equals(children, that.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(names, commands, children);
  }
}
