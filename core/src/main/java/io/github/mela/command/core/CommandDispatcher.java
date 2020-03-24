package io.github.mela.command.core;

import com.google.inject.ImplementedBy;
import javax.annotation.Nonnull;

@FunctionalInterface
@ImplementedBy(DefaultDispatcher.class)
public interface CommandDispatcher {

  void dispatch(@Nonnull String command, @Nonnull CommandContext context);

}
