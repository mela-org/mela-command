package com.github.stupremee.mela.command.binder;

public interface CommandNode {

  CommandNode group(String... aliases);

  CommandNode parent();

  CommandNode bind(Class<?> commandClass);

}
