package io.github.mela.command.core;

import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandTest {

  @Test
  public void testSimpleCommand() {
    SimpleCommand command = new SimpleCommand();
    CommandGroup group = GroupBuilder.create()
        .group("foo")
          .withCommand(command)
        .root()
        .compileIdentity();
    Dispatcher dispatcher = new DefaultDispatcher(group);
    CommandContext context = CommandContext.of(Map.of("env", "test"));
    dispatcher.dispatch("foo\n   bar   \nbaz", context);
    assertTrue(command.executed, "Command was not executed");
    assertEquals("baz", command.arguments);
    assertEquals(context, command.context);
  }

  private static final class SimpleCommand extends CommandCallableAdapter {

    boolean executed = false;
    String arguments = null;
    CommandContext context = null;

    SimpleCommand() {
      super(Set.of("bar"), "executes a simple command", "bar");
    }

    @Override
    public void call(@Nonnull String arguments, @Nonnull CommandContext context) {
      this.executed = true;
      this.arguments = arguments;
      this.context = context;
    }
  }
}
