package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.inject.RootGroupProvider;
import com.google.inject.ProvidedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

@ProvidedBy(RootGroupProvider.class)
public interface CommandGroup {

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
}
