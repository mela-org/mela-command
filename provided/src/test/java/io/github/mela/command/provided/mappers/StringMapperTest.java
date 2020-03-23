package io.github.mela.command.provided.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class StringMapperTest extends BindingTest<StringMapperTest.TestCommand> {

  protected StringMapperTest() {
    super(TestCommand::new);
  }

  @Test
  void testStringMapper() {
    dispatcher.dispatch("foo \"something else\"", CommandContext.create());
    assertEquals("something else", command.value);
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder.bindMapper(String.class, new StringMapper());
  }

  public static final class TestCommand {
    private String value;

    @Command(labels = "foo")
    public void execute(String value) {
      this.value = value;
    }
  }
}