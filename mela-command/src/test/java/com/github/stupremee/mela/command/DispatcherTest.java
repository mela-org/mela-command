package com.github.stupremee.mela.command;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class DispatcherTest {

  private static final String NAME = "sample";

  private SampleCommand command;
  private Dispatcher dispatcher;

  @BeforeEach
  public void setUp() {
    Injector injector = Guice.createInjector(new TestModule());
    this.dispatcher = injector.getInstance(Dispatcher.class);
    this.command = new SampleCommand();
    this.dispatcher.registerCommands(command);
  }

  @AfterEach
  public void tearDown() {
    this.dispatcher = null;
  }

  @Test
  public void testCommandRegistration() {
    boolean wasRegistered = dispatcher.getCommands()
        .stream()
        .map(CommandCallable::getDetails)
        .map(CommandDetails::getAliases)
        .anyMatch((aliases) -> aliases.contains(NAME));
    assertTrue(wasRegistered, "Command was not registered");
  }

  @Test
  public void testCommandExecution() {
    boolean success = dispatcher.call(NAME, new CommandContext());
    assertTrue(success, "Command execution failed");
  }

  private static class SampleCommand {
    @Command(aliases = NAME)
    public void sampleCommand() {

    }
  }

}
