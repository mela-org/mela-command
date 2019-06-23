package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.binding.CommandBinder;
import com.github.stupremee.mela.command.binding.CommandBindingNode;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class InternalCommandBinder implements CommandBinder {

  @Override
  public CommandBindingNode parentNode() {
    return null;
  }

}
