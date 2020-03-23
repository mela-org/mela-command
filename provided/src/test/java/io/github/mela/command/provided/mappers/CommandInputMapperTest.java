package io.github.mela.command.provided.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.CommandInput;
import io.github.mela.command.provided.BindingTest;
import org.junit.jupiter.api.Test;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class CommandInputMapperTest extends BindingTest<CommandInputMapperTest.TestCommand> {

  private CommandInputMapper mapper;

  protected CommandInputMapperTest() {
    super(TestCommand::new);
  }

  @Test
  void testCommandInputMapper() {
    mapper.setGroup(root);
    CommandInput input = CommandInput.parse(root, "bar \"something \" a value -f u");
    dispatcher.dispatch("foo \"bar \\\"something \\\" a value -f u\"",
        CommandContext.create());
    assertEquals(input, command.value, "Argument was not mapped correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    mapper = new CommandInputMapper();
    return builder.bindMapper(CommandInput.class, mapper);
  }

  public static final class TestCommand {
    private CommandInput value;

    @Command(labels = "foo")
    public void execute(CommandInput value) {
      this.value = value;
    }
  }

}
