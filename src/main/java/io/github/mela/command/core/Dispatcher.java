package io.github.mela.command.core;

import com.google.inject.ImplementedBy;
import javax.annotation.Nonnull;

@ImplementedBy(DefaultDispatcher.class)
public interface Dispatcher {

  boolean dispatch(@Nonnull String command, @Nonnull CommandContext context);

}
