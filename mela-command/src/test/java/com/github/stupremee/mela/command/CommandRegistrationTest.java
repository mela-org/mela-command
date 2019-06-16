package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.annotation.CommandClass;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sk89q.intake.Command;
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

  private static final String COMMAND_NAME = "sample";

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
    assertCommandExistance();
  }

  @Test
  public void testManualRegistration() {
    ParametricBuilder builder = injector.getInstance(ParametricBuilder.class);
    builder.registerMethodsAsCommands(dispatcher, new SampleCommand());
    assertCommandExistance();
  }

  private void assertCommandExistance() {
    Assertions.assertTrue(dispatcher.contains(COMMAND_NAME), "Command was not registered");
  }

  @CommandClass
  private static class SampleCommand {
    @Command(aliases = COMMAND_NAME, desc = "No description")
    public void sampleCommand() {

    }
  }


}
