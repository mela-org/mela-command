package io.github.mela.command.compile;

import com.google.inject.ImplementedBy;
import io.github.mela.command.core.CommandCallable;
import java.util.Set;
import javax.annotation.Nonnull;

@ImplementedBy(IdentityCompiler.class)
public interface CommandCompiler {

  @Nonnull
  Set<? extends CommandCallable> compile(@Nonnull Object command);

}
