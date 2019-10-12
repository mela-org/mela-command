package io.github.mela.command.intercept;

<<<<<<< HEAD:mela-command/src/test/java/com/github/stupremee/mela/command/intercept/CommandInterceptorTest.java
import com.github.stupremee.mela.command.core.CommandContext;
import com.github.stupremee.mela.command.core.Dispatcher;
import com.github.stupremee.mela.command.SingleSubjectTest;
=======
import io.github.mela.command.CommandContext;
import io.github.mela.command.Dispatcher;
import io.github.mela.command.SingleSubjectTest;
>>>>>>> 128ccf988eb675eabc1b310c2c0da95b0e6d2ee8:mela-command/src/test/java/io/github/mela/command/intercept/CommandInterceptorTest.java
import com.google.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandInterceptorTest extends SingleSubjectTest<InterceptorTestCommand> {

  @Inject
  private Dispatcher dispatcher;

  protected CommandInterceptorTest() {
    super(InterceptorTestModule::new);
  }

  @Test
  public void testInterceptedCommand() {
    dispatcher.dispatch("intercept", CommandContext.create());
    assertFalse(getSubject().isExecuted(), "Command was executed although intercepted");
  }

  @Test
  public void testNotInterceptedCommand() {
    dispatcher.dispatch("nointercept", CommandContext.create());
    assertTrue(getSubject().isExecuted(), "Command was not executed");
  }

}
