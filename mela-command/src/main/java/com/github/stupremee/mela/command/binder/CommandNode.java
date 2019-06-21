package com.github.stupremee.mela.command.binder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CommandNode {

  CommandNode group(String... aliases);

  CommandNode parent();

  CommandNode bind(Class<?> commandClass);

  List<String> aliases();

  Set<CommandNode> children();

}
