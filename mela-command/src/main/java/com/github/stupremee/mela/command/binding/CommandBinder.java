package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.internal.InternalCommandBinder;
import com.google.inject.Binder;

public interface CommandBinder {

  CommandBindingNode parentNode();

  static CommandBinder create(Binder binder) {
    return new InternalCommandBinder(binder);
  }
}
