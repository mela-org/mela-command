package io.github.mela.command.bind;

import io.github.mela.command.core.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MethodHandleCallableTest {

  @Test
  void testCommandExecution() {
    BindingCommand command = new BindingCommand();
    CommandGroup group = GroupBuilder.create()
        .withCommand(command)
        .compile(new MethodHandleCompiler(CommandBindings.EMPTY));
    Dispatcher dispatcher = new DefaultDispatcher(group);
    dispatcher.dispatch("foo", CommandContext.create());
    assertTrue(command.executed, "Command was not executed");
  }

  public static class BindingCommand {

    private boolean executed = false;

    @Command(labels = "foo")
    public void execute() {
      this.executed = true;
    }
  }
}
