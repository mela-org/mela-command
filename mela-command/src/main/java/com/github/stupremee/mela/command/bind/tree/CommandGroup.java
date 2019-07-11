package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.inject.RootGroupProvider;
import com.google.common.base.Preconditions;
import com.google.inject.ProvidedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@ProvidedBy(RootGroupProvider.class)
public interface CommandGroup {

  Pattern SPLIT_PATTERN = Pattern.compile("\\s+");

  @Nullable
  CommandGroup getParent();

  @Nonnull
  Set<CommandGroup> getChildren();

  @Nonnull
  Set<String> getNames();

  @Nonnull
  Set<CommandCallable> getCommands();

  default boolean matches(String descriptor) {
    String[] parts = SPLIT_PATTERN.split(checkNotNull(descriptor));
    CommandGroup current = this;
    for (int i = parts.length - 1; i >= 0; i--) {
      if (current == null || !current.getNames().contains(parts[i]))
        return false;
      current = current.getParent();
    }
    return true;
  }

  default String getPrimaryName() {
    Set<String> names = getNames();
    return names.isEmpty() ? null : names.iterator().next();
  }

}
