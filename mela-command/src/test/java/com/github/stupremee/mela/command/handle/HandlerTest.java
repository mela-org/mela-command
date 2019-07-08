package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.Dispatcher;
import com.github.stupremee.mela.command.SingleSubjectTest;
import com.google.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class HandlerTest extends SingleSubjectTest<TestExceptionHandler> {

  @Inject
  private Dispatcher dispatcher;

  protected HandlerTest() {
    super(HandlerTestModule::new);
  }

  @Test
  public void testHandler() {
    dispatcher.dispatch("throw", CommandContext.create());
    assertTrue(getSubject().wasHandled(), "Exception was not handled by bound handler");
  }

}
