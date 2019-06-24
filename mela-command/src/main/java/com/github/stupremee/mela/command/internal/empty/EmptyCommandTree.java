package com.github.stupremee.mela.command.internal.empty;

import com.github.stupremee.mela.command.compile.CommandTree;
import com.github.stupremee.mela.command.compile.RecursiveCommandTree;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class EmptyCommandTree extends RecursiveCommandTree<EmptyGroup> {

  public static final EmptyCommandTree INSTANCE = new EmptyCommandTree();

  private EmptyCommandTree() {
    super(EmptyGroup.INSTANCE);
  }

  @Nonnull
  @Override
  public CommandTree merge(@Nonnull CommandTree other) {
    return other;
  }

}
