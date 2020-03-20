package io.github.mela.command.bind;

import io.github.mela.command.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class ExceptionHandlerTest {

  private TestHandler handler;
  private Dispatcher dispatcher;

  @BeforeEach
  void setUp() {
    handler = new TestHandler();
    CommandBindings bindings = CommandBindingsBuilder.create()
        .bindHandler(RuntimeException.class, handler)
        .build();
    CommandGroup group = GroupBuilder.create()
        .withCommand(new TestCommand())
        .compile(new MethodHandleCompiler(bindings));
    dispatcher = new DefaultDispatcher(group);
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
