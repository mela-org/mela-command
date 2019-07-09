package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.inject.MergingCommandTreeProvider;
import com.google.inject.ProvidedBy;

import javax.annotation.Nonnull;

@ProvidedBy(MergingCommandTreeProvider.class)
public interface CommandTree {

  CommandTree EMPTY = EmptyCommandTree.INSTANCE;

  @Nonnull
  CommandTree merge(@Nonnull CommandTree other);

  @Nonnull
  Group getCurrent();

  void stepUp();

  default void stepToRoot() {
    while (!isAtRoot())
      stepUp();
  }

  void stepDown(@Nonnull Group child);

  boolean isAtRoot();

}
