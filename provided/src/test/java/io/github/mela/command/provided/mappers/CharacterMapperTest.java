package io.github.mela.command.provided.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class CharacterMapperTest extends BindingTest<CharacterMapperTest.TestCommand> {

  protected CharacterMapperTest() {
    super(TestCommand::new);
  }

  @Test
  void testCharacterMapper() {
    dispatcher.dispatch("foo f", CommandContext.create());
    assertEquals('f', command.value, "Argument was not mapped correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder.bindMapper(char.class, new CharacterMapper());
  }

  public static final class TestCommand {
    private char value;

    @Command(labels = "foo")
    public void execute(char value) {
      this.value = value;
    }
  }
}