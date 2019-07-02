package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.bind.tree.CommandTree;

import javax.annotation.Nonnull;

// @ImplementedBy
public interface CommandCompiler {

  @Nonnull
  CommandCallable compile(CommandTree tree);

}
