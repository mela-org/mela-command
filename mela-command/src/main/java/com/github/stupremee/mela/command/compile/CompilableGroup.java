package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.GroupBindings;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public interface CompilableGroup extends CommandGroup {

  @Nonnull
  CompilableGroup merge(@Nonnull CompilableGroup other);

  @Nonnull
  GroupBindings getBindings();

  @Nonnull
  Collection<?> getUncompiledCommands();

  @Nonnull
  @Override
  default Set<CommandCallable> getCommands() {
    return Collections.emptySet();
  }
}
