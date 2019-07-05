package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.inject.RootGroupProvider;
import com.google.inject.ProvidedBy;

import java.util.Set;

@ProvidedBy(RootGroupProvider.class)
public interface CommandGroup {

  boolean call(String command, CommandContext context);

  Set<CommandGroup> getChildren();

  Set<CommandCallable> getCommands();

  Set<String> getAliases();

}
