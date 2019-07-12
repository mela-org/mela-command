package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.GroupBindings;

import javax.annotation.Nonnull;
import java.util.Set;

// @ImplementedBy
public interface CommandCompiler {

  @Nonnull
  Set<CommandCallable> compile(@Nonnull Object command, @Nonnull GroupBindings bindings);

}
