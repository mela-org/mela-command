package com.github.stupremee.mela.command.intake;

import com.sk89q.intake.parametric.AbstractModule;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CustomArgumentModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(CustomArgument.class).toProvider(new CustomArgumentProvider());
  }

}
