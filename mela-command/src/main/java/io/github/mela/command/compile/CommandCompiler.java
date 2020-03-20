package io.github.mela.command.compile;

import com.google.inject.ProvidedBy;
import io.github.mela.command.bind.guice.MethodHandleCompilerProvider;
import io.github.mela.command.core.CommandCallable;

import javax.annotation.Nonnull;
import java.util.Set;

@ProvidedBy(MethodHandleCompilerProvider.class)
public interface CommandCompiler {

  @Nonnull
  Set<? extends CommandCallable> compile(@Nonnull Object command);

}
