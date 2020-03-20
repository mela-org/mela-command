package io.github.mela.command.compile;

import com.google.inject.ImplementedBy;
import io.github.mela.command.bind.MethodHandleCompiler;
import io.github.mela.command.core.CommandCallable;

import javax.annotation.Nonnull;
import java.util.Set;

@ImplementedBy(MethodHandleCompiler.class)
public interface CommandCompiler {

  @Nonnull
  Set<? extends CommandCallable> compile(@Nonnull Object command);

}
