package io.github.mela.command.bind;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.DefaultDispatcher;
import io.github.mela.command.core.CommandDispatcher;
import io.github.mela.command.core.ImmutableGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class MethodHandleCallableTest {

  private BindingCommand command;
  private CommandDispatcher dispatcher;

  @BeforeEach
  void setUp() {
    command = new BindingCommand();
    CommandGroup group = ImmutableGroup.builder()
        .add(command)
        .compile(new MethodHandleCompiler(CommandBindings.EMPTY));
    dispatcher = new DefaultDispatcher(group);
  }

  @Test
  void testCommandExecution() {
    dispatcher.dispatch("foo", CommandContext.create());
    assertTrue(command.executed, "Command was not executed");
  }

  @Test
  void testInapplicableArguments() {
    assertThrows(RuntimeException.class, () -> dispatcher.dispatch("foo bar", CommandContext.create()),
        "dispatch did not throw exception despite too many arguments");
  }

  public static final class BindingCommand {

    private boolean executed = false;

    @Command(labels = "foo")
    public void execute() {
      this.executed = true;
    }
  }
}
