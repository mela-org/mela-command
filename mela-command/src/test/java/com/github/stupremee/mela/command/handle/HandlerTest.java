package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandContext;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class HandlerTest {

  @Inject
  private CommandCallable callable;
  private TestExceptionHandler handler;

  @BeforeEach
  public void setUp() {
    handler = new TestExceptionHandler();
    Injector injector = Guice.createInjector(new HandlerTestModule(handler));
    injector.injectMembers(this);
  }

  @AfterEach
  public void tearDown() {
    handler = null;
    callable = null;
  }

  @Test
  public void testHandler() {
    callable.selectChild("throw").callWithRemainingArgs(new CommandContext());
    assertTrue(handler.wasHandled(), "Exception was not handled by bound handler");
  }

}
