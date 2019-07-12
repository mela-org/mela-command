package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.compile.CommandCompiler;

import javax.annotation.Nonnull;

public interface CompilableGroup extends CommandGroup {

  void assimilate(@Nonnull CompilableGroup other);

  void compile(@Nonnull CommandCompiler compiler);

}
