package io.github.mela.command.handle;

<<<<<<< HEAD:mela-command/src/test/java/com/github/stupremee/mela/command/handle/HandlerTest.java
import com.github.stupremee.mela.command.core.CommandContext;
import com.github.stupremee.mela.command.core.Dispatcher;
import com.github.stupremee.mela.command.SingleSubjectTest;
=======
import io.github.mela.command.CommandContext;
import io.github.mela.command.Dispatcher;
import io.github.mela.command.SingleSubjectTest;
>>>>>>> 128ccf988eb675eabc1b310c2c0da95b0e6d2ee8:mela-command/src/test/java/io/github/mela/command/handle/HandlerTest.java
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
