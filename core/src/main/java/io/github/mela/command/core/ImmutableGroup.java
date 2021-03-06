package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ImmutableGroup implements CommandGroup {

  public static final ImmutableGroup EMPTY = childlessRoot(ImmutableSet.of());

  private final ImmutableGroup parent;
  private final Set<String> names;
  private final Set<CommandCallable> commands;

  private Set<ImmutableGroup> children;

  private ImmutableGroup(
      ImmutableGroup parent, Set<String> names, Set<? extends CommandCallable> commands) {
    this.parent = parent;
    this.names = ImmutableSet.copyOf(names);
    this.commands = ImmutableSet.copyOf(commands);
  }

  @Nonnull
  public static CommandGroup merge(@Nonnull CommandGroup... rootGroups) {
    return merge(Arrays.asList(rootGroups));
  }

  @Nonnull
  public static CommandGroup merge(@Nonnull Collection<? extends CommandGroup> rootGroups) {
    checkArgument(rootGroups.stream().allMatch(CommandGroup::isRoot),
        "Provided groups must all be root root groups");
    return rootGroups.stream().map(MutableGroup::new)
        .reduce((one, two) -> {
          assimilate(one, two);
          return one;
        }).map(ImmutableGroup::copyOf)
        .orElse(ImmutableGroup.EMPTY);
  }

  private static void assimilate(MutableGroup one, MutableGroup two) {
    one.commands.addAll(two.commands);
    for (MutableGroup otherChild : two.children) {
      Optional<MutableGroup> ownChild = one.children.stream()
          .filter((g) -> g.names.equals(otherChild.names))
          .findFirst();
      if (ownChild.isPresent()) {
        assimilate(ownChild.get(), otherChild);
      } else {
        one.children.add(otherChild);
      }
    }
  }

  private static class MutableGroup implements CommandGroup {

    private final MutableGroup parent;
    private final Set<MutableGroup> children;
    private final Set<String> names;
    private final Set<CommandCallable> commands;

    // creates empty
    MutableGroup(MutableGroup parent) {
      this.parent = parent;
      commands = Sets.newLinkedHashSet();
      names = Sets.newLinkedHashSet();
      children = Sets.newLinkedHashSet();
    }

    // copies group
    MutableGroup(CommandGroup group) {
      this(null, group);
    }

    // copies group with parent
    MutableGroup(MutableGroup parent, CommandGroup group) {
      this.parent = parent;
      commands = Sets.newLinkedHashSet(group.getCommands());
      names = group.getNames();
      children = group.getChildren().stream()
          .map(MutableGroup::new)
          .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Nonnull
    @Override
    public Optional<MutableGroup> getParent() {
      return Optional.ofNullable(parent);
    }

    @Nonnull
    @Override
    public Set<MutableGroup> getChildren() {
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
  }


  @CheckReturnValue
  @Nonnull
  public static ImmutableGroupBuilder builder() {
    return new ImmutableGroupBuilder();
  }

  @Nonnull
  public static ImmutableGroup childlessRoot(@Nonnull Set<CommandCallable> commands) {
    ImmutableGroup group = new ImmutableGroup(null, ImmutableSet.of(), commands);
    group.setChildren(ImmutableSet.of());
    return group;
  }

  @Nonnull
  public static ImmutableGroup copyOf(@Nonnull CommandGroup group) {
    return checkNotNull(group) instanceof ImmutableGroup
        ? (ImmutableGroup) group
        : of(group, GroupAssembler.forGroup());
  }

  @Nonnull
  public static <T> ImmutableGroup of(@Nonnull T root, @Nonnull GroupAssembler<T> assembler) {
    checkNotNull(root);
    checkNotNull(assembler);
    Set<? extends CommandCallable> commands = ImmutableSet.copyOf(assembler.getCommands(root));
    checkForDuplicateNames(commands, CommandCallable::getLabels);
    checkOnlyOneEmptyLabelCommand(commands);
    ImmutableGroup group = new ImmutableGroup(null,
        ImmutableSet.copyOf(assembler.getNames(root)), commands);
    group.setChildren(deepChildrenCopy(root, assembler, group));
    return group;
  }

  private static <T> Set<ImmutableGroup> deepChildrenCopy(T template, GroupAssembler<T> assembler,
                                                          ImmutableGroup current) {
    Set<ImmutableGroup> groupChildren = Sets.newLinkedHashSet();
    Set<? extends T> children = assembler.getChildren(template);
    checkForDuplicateNames(children, assembler::getNames);
    for (T child : children) {
      Set<String> names = ImmutableSet.copyOf(assembler.getNames(child));
      checkArgument(!names.isEmpty(),
          "No group except for the root group may have an empty set of names");
      Set<? extends CommandCallable> commands = ImmutableSet.copyOf(assembler.getCommands(child));
      checkOnlyOneEmptyLabelCommand(commands);
      checkForDuplicateNames(commands, CommandCallable::getLabels);
      ImmutableGroup next = new ImmutableGroup(current, names, commands);
      next.setChildren(deepChildrenCopy(child, assembler, next));
      groupChildren.add(next);
    }
    return groupChildren;
  }

  private static void checkOnlyOneEmptyLabelCommand(Set<? extends CommandCallable> commands) {
    checkArgument(commands.stream()
            .map(CommandCallable::getLabels)
            .filter(Set::isEmpty)
            .count() <= 1,
        "There must not be more than one command with empty labels in one group");
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

  @Nonnull
  @Override
  public Optional<ImmutableGroup> getParent() {
    return Optional.ofNullable(parent);
  }

  @Nonnull
  @Override
  public Set<ImmutableGroup> getChildren() {
    return children;
  }

  @Nonnull
  @Override
  public Set<String> getNames() {
    return names;
  }

  @Nonnull
  @Override
  public Set<? extends CommandCallable> getCommands() {
    return commands;
  }

  @Override
  public String toString() {
    StringBuilder pathBuilder = new StringBuilder();
    for (ImmutableGroup group = this; group != null; group = group.parent) {
      pathBuilder.insert(0, group.names);
      if (group.parent != null) {
        pathBuilder.insert(0, " - ");
      }
    }
    return pathBuilder.insert(0, "ImmutableGroup(").append(")").toString();
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
