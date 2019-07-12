package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.compile.CommandCompiler;

public interface CompilableGroup extends CommandGroup {

  void assimilate(CompilableGroup other);

  void compile(CommandCompiler compiler);

}
