package io.github.mela.command.compile;

import io.github.mela.command.core.CommandCallable;
import java.util.Set;
import javax.annotation.Nonnull;

public interface CommandCompiler {

  @Nonnull
  Set<? extends CommandCallable> compile(@Nonnull Object command);

}
