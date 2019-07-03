package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.CommandTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class HandlerTest extends CommandTest<TestExceptionHandler> {

  protected HandlerTest() {
    super(HandlerTestModule::new);
  }

  @Test
  public void testHandler() {
    callCommand("throw");
    assertTrue(getSubject().wasHandled(), "Exception was not handled by bound handler");
  }

}
