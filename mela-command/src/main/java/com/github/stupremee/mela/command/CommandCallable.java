package com.github.stupremee.mela.command;

import java.util.Set;

public interface CommandCallable {

  boolean call(String arguments, CommandContext context);

  Set<CommandCallable> getChildren();

  CommandDetails getDetails();

}
