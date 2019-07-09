package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.bind.tree.Group;

import javax.annotation.Nonnull;
import java.util.Set;

// @ImplementedBy
public interface CommandCompiler {

  @Nonnull
  Set<CommandCallable> compile(@Nonnull Group group);

}
