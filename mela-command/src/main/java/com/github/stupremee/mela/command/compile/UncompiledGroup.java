package com.github.stupremee.mela.command.compile;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

public interface UncompiledGroup {

  @Nonnull
  UncompiledGroup merge(@Nonnull UncompiledGroup other);

  @Nonnull
  Collection<?> getUncompiledCommands();

  @Nonnull
  Set<? extends UncompiledGroup> getChildren();

  @Nonnull
  Set<String> getNames();

}
