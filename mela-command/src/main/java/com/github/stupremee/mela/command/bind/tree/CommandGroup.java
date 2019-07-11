package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.inject.RootGroupProvider;
import com.google.inject.ProvidedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@ProvidedBy(RootGroupProvider.class)
public interface CommandGroup {

  @Nullable
  CommandGroup getParent();

  @Nonnull
  Set<CommandGroup> getChildren();

  @Nonnull
  Set<String> getNames();

  @Nonnull
  Set<CommandCallable> getCommands();

  default boolean matches(String descriptor) {
    return false;
  }

  default String getPrimaryName() {
    return null;
  }

}
