package com.github.stupremee.mela.command.binding;

import com.google.inject.AbstractModule;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class SingleChildModule extends AbstractModule {

  @Override
  protected void configure() {
    CommandBinder.create(binder()).parentNode()
        .group("child");
  }
}
