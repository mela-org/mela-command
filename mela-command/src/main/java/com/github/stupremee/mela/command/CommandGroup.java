package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.inject.RootScopeProvider;
import com.google.inject.ProvidedBy;

import java.util.Set;

@ProvidedBy(RootScopeProvider.class)
public interface CommandGroup {

  boolean call(String command, CommandContext context);

  Set<CommandGroup> getChildren();

  Set<CommandCallable> getCommands();

  Set<String> getAliases();

}
