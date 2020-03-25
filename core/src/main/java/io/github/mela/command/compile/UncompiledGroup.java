package io.github.mela.command.compile;

import java.util.Collection;
import java.util.Set;
import javax.annotation.Nonnull;

public interface UncompiledGroup {

  @Nonnull
  Collection<?> getUncompiledCommands();

  @Nonnull
  Set<? extends UncompiledGroup> getChildren();

  @Nonnull
  Set<String> getNames();

}
