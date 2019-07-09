package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.inject.RootGroupProvider;
import com.google.inject.ProvidedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@ProvidedBy(RootGroupProvider.class)
public interface CommandGroup {

  Pattern SPLIT_PATTERN = Pattern.compile("\\s+");

  @Nullable
  CommandGroup getParent();

  @Nonnull
  Set<CommandGroup> getChildren();

  @Nonnull
  Set<CommandCallable> getCommands();

  @Nullable
  default String getPrimaryName() {
    Set<String> names = getNames();
    return names.isEmpty() ? null : names.iterator().next();
  }

  @Nonnull
  Set<String> getNames();

  default boolean matches(String descriptor) {
    String[] parts = SPLIT_PATTERN.split(descriptor);
    CommandGroup current = this;
    for (int i = parts.length - 1; i >= 0; i--) {
      if (current == null || !current.getNames().contains(parts[i]))
        return false;
      current = current.getParent();
    }
    return true;
  }
}
