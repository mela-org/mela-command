package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.compile.CommandCompiler;

import javax.annotation.Nonnull;

public interface Compilable {

  Compilable assimilate(@Nonnull Compilable other);

  CommandGroup compile(@Nonnull CommandCompiler compiler);

}
