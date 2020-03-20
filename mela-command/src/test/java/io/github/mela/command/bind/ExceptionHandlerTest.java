package io.github.mela.command.bind;

import io.github.mela.command.core.CommandCallable;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class ExceptionHandlerTest extends BindingTest<ExceptionHandlerTest.TestCommand> {

  private TestHandler handler;

  protected ExceptionHandlerTest() {
    super(TestCommand::new);
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    handler = new TestHandler();
    return builder.bindHandler(RuntimeException.class, handler);
  }

  @Test
  void testHandlerExecution() {
    dispatcher.dispatch("foo", CommandContext.create());
    assertTrue(handler.executed, "Exception handler was not executed");
  }

  public static final class TestCommand {

    @Command(labels = "foo")
    public void execute() {
      throw new RuntimeException();
    }
  }

  private static class TestHandler implements ExceptionHandler<RuntimeException> {

    boolean executed;

    @Override
    public void handle(
        @Nonnull RuntimeException exception, @Nonnull CommandCallable command, @Nonnull CommandContext context) {
      executed = true;
    }
  }

}
