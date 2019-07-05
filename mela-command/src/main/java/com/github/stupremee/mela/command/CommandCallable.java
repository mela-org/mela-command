package com.github.stupremee.mela.command;

import java.util.Set;

public interface CommandCallable {

  void call(String arguments, CommandContext context);

  Set<String> getAliases();

  String getHelp();

  String getDescription();

  String getUsage();

}
