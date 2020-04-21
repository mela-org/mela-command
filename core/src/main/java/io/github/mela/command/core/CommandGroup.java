package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.inject.ProvidedBy;
import io.github.mela.command.guice.CompilingRootGroupProvider;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nonnull;

/**
 * {@code CommandGroup}s are used to group commands that have a common word prefix.
 * For example, a command labelled "bar" in a group named "foo" could be dispatched via
 * "foo bar" starting from the root group.
 *
 * A group may contain any amount of child groups, meaning that each group is essentially
 * a node in a command tree.
 * In every command tree, there is a root group that has no names and no parent group.
 *
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@ProvidedBy(CompilingRootGroupProvider.class)
public interface CommandGroup {

  @Nonnull
  Optional<? extends CommandGroup> getParent();

  @Nonnull
  Set<? extends CommandGroup> getChildren();

  @Nonnull
  Set<String> getNames();

  @Nonnull
  Set<? extends CommandCallable> getCommands();

  @Nonnull
  default Optional<? extends CommandGroup> findChild(@Nonnull String name) {
    checkNotNull(name);
    return getChildren()
        .stream()
        .filter((group) -> group.getNames().stream().anyMatch(name::equalsIgnoreCase))
        .findFirst();
  }

  @Nonnull
  default Optional<? extends CommandCallable> findCommand(@Nonnull String name) {
    checkNotNull(name);
    return getCommands()
        .stream()
        .filter((command) -> command.getLabels().stream().anyMatch(name::equalsIgnoreCase))
        .findFirst();
  }

  @Nonnull
  default Optional<? extends CommandCallable> findDefaultCommand() {
    return getCommands()
        .stream()
        .filter((command) -> command.getLabels().isEmpty())
        .findFirst();
  }

  default void walk(@Nonnull Consumer<? super CommandGroup> action) {
    action.accept(this);
    for (CommandGroup child : getChildren()) {
      child.walk(action);
    }
  }

  default boolean isRoot() {
    return !getParent().isPresent() && getNames().isEmpty();
  }

  @Nonnull
  default String getQualifiedName() {
    StringBuilder pathBuilder = new StringBuilder();
    for (CommandGroup group = this; group != null; group = group.getParent().orElse(null)) {
      String primaryName = group.getPrimaryName().orElse("");
      pathBuilder.insert(0, primaryName);
      pathBuilder.insert(0, " ");
    }
    return pathBuilder.toString().trim();
  }

  @Nonnull
  default Optional<String> getPrimaryName() {
    Set<String> names = getNames();
    return names.isEmpty()
        ? Optional.empty()
        : Optional.of(names.iterator().next());
  }
}
