package com.github.stupremee.mela.command;

import com.google.inject.ProvidedBy;

import java.util.List;
import java.util.Set;

@ProvidedBy(CallableProvider.class)
public interface CommandCallable {

  boolean call(String arguments, CommandContext context);

  Set<CommandCallable> getChildren();

  List<String> getAliases();

}
