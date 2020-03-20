package io.github.mela.command.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class DefaultDispatcherTest {

  private SimpleCommand command;
  private Dispatcher dispatcher;

  @BeforeEach
  void setUp() {
    command = new SimpleCommand();
    CommandGroup group = ImmutableGroup.builder()
        .group("foo")
          .withCommand(command)
        .root()
        .build();
    dispatcher = DefaultDispatcher.create(group);
  }

  @Test
  void testSimpleCommandDispatch() {
    CommandContext context = CommandContext.of(Map.of("env", "test"));
    boolean success = dispatcher.dispatch("foo\n \t  bar   \nbaz", context);
    assertTrue(success, "Dispatcher did not return success");
    assertTrue(command.executed, "Command was not executed");
    assertEquals("baz", command.arguments.getRaw(), "command arguments were changed");
    assertEquals(context, command.context, "command context was changed");
  }

  private static class SimpleCommand extends CommandCallableAdapter {

    boolean executed = false;
    Arguments arguments = null;
    CommandContext context = null;

    SimpleCommand() {
      super(Set.of("bar"), "executes a simple command", "baz", "bar");
    }

    @Override
    public void call(@Nonnull Arguments arguments, @Nonnull CommandContext context) {
      this.executed = true;
      this.arguments = arguments;
      this.context = context;
    }
  }
}
