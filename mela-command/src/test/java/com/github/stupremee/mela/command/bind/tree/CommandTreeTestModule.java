package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.bind.CommandBinder;
import com.google.inject.AbstractModule;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class CommandTreeTestModule extends AbstractModule {

  @Override
  protected void configure() {
    CommandBinder.create(binder()).root()
        .group("child");
  }
}
