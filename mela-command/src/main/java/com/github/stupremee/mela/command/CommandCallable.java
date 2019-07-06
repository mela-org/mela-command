package com.github.stupremee.mela.command;

import javax.annotation.Nonnull;
import java.util.Set;

public interface CommandCallable {

  void call(String arguments, CommandContext context);

  @Nonnull
  Set<String> getAliases();

  String getHelp();

  String getDescription();

  String getUsage();

}
