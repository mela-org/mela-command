package io.github.mela.command.provided.mappers;

import static org.junit.jupiter.api.Assertions.assertTrue;


import io.github.mela.command.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class BooleanMapperTest extends BindingTest<BooleanMapperTest.TestCommand> {

  protected BooleanMapperTest() {
    super(TestCommand::new);
  }

  @Test
  void testBooleanMapper() {
    dispatcher.dispatch("foo true", CommandContext.create());
    assertTrue(command.value, "Argument was not mapped correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder.bindMapper(boolean.class, new BooleanMapper());
  }

  public static final class TestCommand {

    private boolean value;

    @Command(labels = "foo")
    public void execute(boolean value) {
      this.value = value;
    }
  }
}