package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.provider.DefaultCallableProvider;
import com.google.inject.ProvidedBy;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ProvidedBy(DefaultCallableProvider.class)
public interface CommandCallable {

  boolean call(String arguments, CommandContext context);

  Optional<CommandCallable> selectChild(String arguments);

  Set<CommandCallable> getChildren();

  List<String> getAliases();

}
