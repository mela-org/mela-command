package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.binding.internal.InternalCommandBinder;
import com.google.inject.Binder;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

public interface CommandBinder {

  CommandBindingNode parentNode();

  static CommandBinder create(@Nonnull Binder binder) {
    return new InternalCommandBinder(checkNotNull(binder));
  }
}
