package io.github.mela.command.bind.provided.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.bind.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class CommandContextMapperTest extends BindingTest<CommandContextMapperTest.TestCommand> {

  protected CommandContextMapperTest() {
    super(TestCommand::new);
  }

  @Test
  void testCommandContextMapper() {
    CommandContext context = CommandContext.create();
    context.put("key", "value");
    dispatcher.dispatch("foo", context);
    assertEquals(context, command.context, "CommandContext was not mapped");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder.bindMapper(CommandContext.class, new CommandContextMapper());
  }

  public static final class TestCommand {

    private CommandContext context;

    @Command(labels = "foo")
    public void execute(CommandContext context) {
      this.context = context;
    }

  }
}