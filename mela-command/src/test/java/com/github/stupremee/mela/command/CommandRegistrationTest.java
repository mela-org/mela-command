package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.intake.TestCommand;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sk89q.intake.dispatcher.Dispatcher;
import com.sk89q.intake.parametric.ParametricBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandRegistrationTest {

  private Injector injector;
  private Dispatcher dispatcher;

  @BeforeEach
  public void setUp() {
    this.injector = Guice.createInjector(IntakeModule.create());
    this.dispatcher = injector.getInstance(Dispatcher.class);
  }

  @AfterEach
  public void tearDown() {
    this.injector = null;
    this.dispatcher = null;
  }

  @Test
  public void testClasspathRegistration() {
    Commands.registerFromClasspath(injector);
    assertCommandExistence();
  }

  @Test
  public void testManualRegistration() {
    ParametricBuilder builder = injector.getInstance(ParametricBuilder.class);
    TestCommand command = injector.getInstance(TestCommand.class);
    builder.registerMethodsAsCommands(dispatcher, command);
    assertCommandExistence();
  }

  private void assertCommandExistence() {
    Assertions.assertTrue(dispatcher.contains(TestCommand.NAME), "Command was not registered");
  }



}
