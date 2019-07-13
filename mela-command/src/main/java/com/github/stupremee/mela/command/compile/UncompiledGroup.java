package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.GroupBindings;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public interface UncompiledGroup {

  @Nonnull
  UncompiledGroup merge(@Nonnull UncompiledGroup other);

  Collection<?> getUncompiledCommands();

  GroupBindings getBindings();

  Set<? extends UncompiledGroup> getChildren();

  Set<String> getNames();

}