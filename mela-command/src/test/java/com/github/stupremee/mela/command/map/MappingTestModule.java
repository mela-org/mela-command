package com.github.stupremee.mela.command.map;

import com.github.stupremee.mela.command.TestModule;
import com.github.stupremee.mela.command.bind.CommandBinder;
import com.google.inject.TypeLiteral;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MappingTestModule extends TestModule {

  private final MappingCommand command;

  public MappingTestModule(MappingCommand command) {
    this.command = command;
  }

  @Override
  protected void configureCommandBindings(CommandBinder binder) {
    binder.root()
        .add(command)
        .bindParameter(ObjectWrapping.class)
          .toMapper(((argument, context) -> new ObjectWrapping(argument))) // TODO: 02.07.2019
        .bindParameter(new TypeLiteral<GenericWrapping<String>>() {})
          .toMapper((argument, context) -> new GenericWrapping<>((String) argument)); // TODO: 02.07.2019
  }
}
