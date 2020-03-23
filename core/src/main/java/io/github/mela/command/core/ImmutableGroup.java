package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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

  // TODO check for duplicate command names
  @Nonnull
  public static <T> CommandGroup of(@Nonnull T root, @Nonnull GroupAssembler<T> assembler) {
    checkNotNull(root);
    checkNotNull(assembler);
    ImmutableGroup group = new ImmutableGroup(null, assembler.getNames(root),
        assembler.getCommands(root));
    group.setChildren(deepChildrenCopy(root, assembler, group));
    return group;
  }

  private static <T> Set<ImmutableGroup> deepChildrenCopy(T template, GroupAssembler<T> assembler,
                                                          ImmutableGroup current) {
    Set<ImmutableGroup> groupChildren = Sets.newHashSet();
    Set<? extends T> children = assembler.getChildren(template);
    checkForDuplicateNames(children, assembler::getNames);
    for (T child : children) {
      Set<String> names = assembler.getNames(child);
      Set<? extends CommandCallable> commands = assembler.getCommands(child);
      checkForDuplicateNames(commands, CommandCallable::getLabels);
      ImmutableGroup next = new ImmutableGroup(current, names, commands);
      next.setChildren(deepChildrenCopy(child, assembler, next));
      groupChildren.add(next);
    }
    return groupChildren;
  }

  private static <T> void checkForDuplicateNames(
      Set<T> subjects, Function<T, Set<String>> nameFunction) {
    for (T subject : subjects) {
      for (T check : subjects) {
        boolean hasDuplicate = check != subject && nameFunction.apply(check)
            .stream().anyMatch((name) -> nameFunction.apply(subject).contains(name));
        if (hasDuplicate) {
          throw new IllegalArgumentException("Duplicate name: the names of "
              + subject + " and " + check + " are not completely distinct");
        }
      }
    }
  }

  private void setChildren(Set<ImmutableGroup> children) {
    checkState(this.children == null);
    this.children = ImmutableSet.copyOf(children);
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
