package com.github.stupremee.mela.command.intercept;

import com.github.stupremee.mela.command.CommandTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class InterceptorTest extends CommandTest<InterceptorTestCommand> {

  protected InterceptorTest() {
    super(InterceptorTestModule::new);
  }

  @Test
  public void testInterceptedCommand() {
    callCommand("intercept");
    assertFalse(getSubject().isExecuted(), "Command was executed although intercepted");
  }

  @Test
  public void testNotInterceptedCommand() {
    callCommand("nointercept");
    assertTrue(getSubject().isExecuted(), "Command was not executed");
  }

}
