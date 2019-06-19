package com.github.stupremee.mela.command;

import java.util.Set;

public interface CommandCallable {

  void call(String arguments, CommandContext context) throws Exception;

  Set<CommandCallable> getChildren();

  CommandDetails getDetails();

}
