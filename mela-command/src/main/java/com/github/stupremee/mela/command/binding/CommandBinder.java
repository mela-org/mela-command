package com.github.stupremee.mela.command.binding;

import com.google.inject.Binder;

public interface CommandBinder {

  CommandBindingNode parentNode();

  static CommandBinder create(Binder binder) {
    return null;
  }
}
