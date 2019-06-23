package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.binding.CommandBinder;
import com.github.stupremee.mela.command.binding.CommandBindingNode;
import com.google.inject.Binder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class InternalCommandBinder implements CommandBinder {

  private final Binder binder;

  public InternalCommandBinder(Binder binder) {
    this.binder = binder;
  }

  @Override
  public CommandBindingNode parentNode() {
    return new InternalCommandBindingNode(new CommandMultibinder(binder));
  }

}
