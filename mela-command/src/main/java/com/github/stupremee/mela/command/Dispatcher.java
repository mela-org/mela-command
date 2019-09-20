package com.github.stupremee.mela.command;

import com.google.inject.ImplementedBy;

import javax.annotation.Nonnull;

@ImplementedBy(DefaultDispatcher.class)
public interface Dispatcher {

  boolean dispatch(@Nonnull String command, @Nonnull CommandContext context);

}
