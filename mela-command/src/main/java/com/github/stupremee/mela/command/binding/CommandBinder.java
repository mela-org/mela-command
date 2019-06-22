package com.github.stupremee.mela.command.binding;

public interface CommandBinder {

  CommandBindingNode parentNode();

  static CommandBinder create() {
    return null;
  }
}
