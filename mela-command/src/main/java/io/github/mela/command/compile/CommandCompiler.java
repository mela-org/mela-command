package io.github.mela.command.compile;

import io.github.mela.command.CommandCallable;
import io.github.mela.command.bind.ReflectiveCompiler;
import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;
import java.util.Set;

@ImplementedBy(ReflectiveCompiler.class)
public interface CommandCompiler {

  @Nonnull
  Set<? extends CommandCallable> compile(@Nonnull Object command);

}
