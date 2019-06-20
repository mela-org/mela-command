package com.github.stupremee.mela.command;

// @ImplementedBy
public interface CommandCompiler {

  CommandCallable compile(Object module);

}
