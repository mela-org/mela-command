package com.github.stupremee.mela.command.map;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandContext;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingTest {

  private MappingCommand command;

  @Inject
  private CommandCallable callable;

  @BeforeEach
  public void setUp() {
    command = new MappingCommand();
    Injector injector = Guice.createInjector(new MappingTestModule(command));
    injector.injectMembers(this);
  }

  @AfterEach
  public void tearDown() {
    callable = null;
    command = null;
  }

  @Test
  public void testSimpleMapping() {
    callable.selectChild("simple object").callWithRemainingArgs(new CommandContext());
    ObjectWrapping result = command.getSimpleResult();
    assertNotNull(result, "Custom object argument was null");
    assertNotNull(result.getContent(), "Content of result was null");
  }

  @Test
  public void testGenericMapping() {
    callable.selectChild("generic string").callWithRemainingArgs(new CommandContext());
    GenericWrapping<String> result = command.getGenericResult();
    assertNotNull(result, "Custom generic argument was null");
    assertNotNull(result.getContent(), "Content of generic result was null");
  }
}
