package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandCallable;

import javax.annotation.Nonnull;
import java.util.Set;

// @ImplementedBy
public interface CommandCompiler {

  @Nonnull
  Set<? extends CommandCallable> compile(@Nonnull Object command);

}
