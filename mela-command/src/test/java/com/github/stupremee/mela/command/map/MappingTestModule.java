package com.github.stupremee.mela.command.map;

import com.github.stupremee.mela.command.TestModule;
import com.github.stupremee.mela.command.guice.CommandBinder;
import com.google.inject.Binder;
import com.google.inject.TypeLiteral;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class MappingTestModule extends TestModule {

  MappingTestModule() {
    super(new MappingTestCommand());
  }

  @Override
  protected void configureCommandBindings(CommandBinder binder) {
    binder.root()
        .bindParameter(ObjectWrapping.class)
          .toMapper(((argument, context) -> new ObjectWrapping(argument)))
        .bindParameter(new TypeLiteral<GenericWrapping<String>>() {})
          .toMapper((argument, context) -> new GenericWrapping<>((String) argument));
  }

  @Override
  protected void configureNormalBindings(Binder binder) {
    binder.bind(MappingTestCommand.class).toInstance((MappingTestCommand) getRootCommand());
  }
}
