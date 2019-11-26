package io.github.mela.command.core;

import com.google.inject.ProvidedBy;
import io.github.mela.command.bind.guice.CompilingRootGroupProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

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
  Set<CommandCallable> getCommands();

  default boolean matches(@Nonnull String descriptor) {
    String[] parts = SPLIT_PATTERN.split(checkNotNull(descriptor));
    CommandGroup current = this;
    for (int i = parts.length - 1; i >= 0; i--) {
      if (current == null || !current.getNames().contains(parts[i]))
        return false;
      current = current.getParent();
    }
    return true;
  }

  @Nullable
  default String getPrimaryName() {
    Set<String> names = getNames();
    return names.isEmpty() ? null : names.iterator().next();
  }
}
