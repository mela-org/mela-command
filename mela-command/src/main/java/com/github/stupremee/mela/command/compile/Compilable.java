package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandGroup;

import javax.annotation.Nonnull;

public interface Compilable {

  Compilable assimilate(@Nonnull Compilable other);

  CommandGroup compile(@Nonnull CommandCompiler compiler);

}
