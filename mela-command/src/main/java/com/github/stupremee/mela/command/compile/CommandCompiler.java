package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandCallable;

// @ImplementedBy
public interface CommandCompiler {

  CommandCallable compile(CommandTree tree);

}
