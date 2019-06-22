package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.binder.CommandTree;

// @ImplementedBy
public interface CommandCompiler {

  CommandCallable compile(CommandTree tree);

}
