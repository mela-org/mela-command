package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.inject.ProvidedBy;
import io.github.mela.command.guice.CompilingRootGroupProvider;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@ProvidedBy(CompilingRootGroupProvider.class)
public interface CommandGroup {

  Pattern SPLIT_PATTERN = Pattern.compile("\\s+");

  @Nullable
  CommandGroup getParent();

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
    return getParent() == null && getNames().isEmpty();
  }

  @Nonnull
  default String getQualifiedName() {
    StringBuilder pathBuilder = new StringBuilder();
    for (CommandGroup group = this; group != null; group = group.getParent()) {
      String primaryName = group.getPrimaryName();
      pathBuilder.insert(0, primaryName == null ? "" : primaryName);
      pathBuilder.insert(0, " ");
    }
    return pathBuilder.toString().trim();
  }

  @Nullable
  default String getPrimaryName() {
    Set<String> names = getNames();
    return names.isEmpty() ? null : names.iterator().next();
  }
}
