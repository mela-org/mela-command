package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.bind.ReflectiveCompiler;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import java.util.Set;

@ImplementedBy(ReflectiveCompiler.class)
public interface CommandCompiler {

  @Nonnull
  Set<? extends CommandCallable> compile(@Nonnull Object command);

}
