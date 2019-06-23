package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.inject.DefaultCallableProvider;
import com.google.inject.ProvidedBy;

import java.util.Optional;
import java.util.Set;

@ProvidedBy(DefaultCallableProvider.class)
public interface CommandCallable {

  boolean call(String arguments, CommandContext context);

  Optional<CommandCallable> selectChild(String arguments);

  Set<CommandCallable> getChildren();

  Set<String> getAliases();

}
