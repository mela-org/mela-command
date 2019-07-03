package com.github.stupremee.mela.command.intercept;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.TestModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class InterceptorTest {

  @Inject
  private CommandCallable callable;

  private InterceptorTestCommand command;

  @BeforeEach
  public void setUp() {
    command = new InterceptorTestCommand();
    Injector injector = Guice.createInjector(new InterceptorTestModule(command));
    injector.injectMembers(this);
  }

  @AfterEach
  public void tearDown() {
    this.callable = null;
    this.command = null;
  }

  @Test
  public void testInterceptedCommand() {
    callable.selectChild("intercept").callWithRemainingArgs(new CommandContext());
    assertFalse(command.isExecuted(), "Command was executed although intercepted");
  }

  @Test
  public void testNotInterceptedCommand() {
    callable.selectChild("nointercept").callWithRemainingArgs(new CommandContext());
    assertTrue(command.isExecuted(), "Command was not executed");
  }

}
