package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.inject.DefaultCallableProvider;
import com.google.inject.ProvidedBy;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

@ProvidedBy(DefaultCallableProvider.class)
public interface CommandCallable {

  void call(String arguments, CommandContext context);

  @CheckReturnValue
  boolean hasCommand();

  @Nonnull
  @CheckReturnValue
  Selection selectChild(String arguments);

  @Nonnull
  Set<CommandCallable> getChildren();

  @Nonnull
  Set<String> getAliases();

  interface Selection {

    Optional<CommandCallable> result();

    boolean callWithRemainingArgs(CommandContext context);

  }

}
