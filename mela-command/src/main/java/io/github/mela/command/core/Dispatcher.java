package io.github.mela.command.core;

import com.google.inject.ProvidedBy;
import io.github.mela.command.bind.guice.DefaultDispatcherProvider;

import javax.annotation.Nonnull;

@ProvidedBy(DefaultDispatcherProvider.class)
public interface Dispatcher {

  boolean dispatch(@Nonnull String command, @Nonnull CommandContext context);

}
