package io.github.mela.command.compile;

import com.google.inject.ImplementedBy;
import io.github.mela.command.bind.ReflectiveCompiler;
import io.github.mela.command.core.CommandCallable;

import javax.annotation.Nonnull;
import java.util.Set;

@ImplementedBy(ReflectiveCompiler.class)
public interface CommandCompiler {

  @Nonnull
  Set<? extends CommandCallable> compile(@Nonnull Object command);

}
