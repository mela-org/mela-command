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

  private Dispatcher dispatcher;

  @BeforeEach
  public void setUp() {
    Injector injector = Guice.createInjector(new TestModule());
    this.dispatcher = injector.getInstance(Dispatcher.class);
  }

  @AfterEach
  public void tearDown() {
    this.dispatcher = null;
  }

  @Test
  public void testCommandExecution() {
    registerCommand();
    boolean success = dispatcher.call("sample", new CommandContext());
    assertTrue(success, "Command execution failed");
  }

  private void registerCommand() {
    this.dispatcher.registerCommands(new SampleCommand());
  }

  private static class SampleCommand {
    @Command(aliases = "sample")
    public void sampleCommand() {

    }
  }

}
