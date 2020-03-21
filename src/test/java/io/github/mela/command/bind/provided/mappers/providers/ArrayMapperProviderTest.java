package io.github.mela.command.bind.provided.mappers.providers;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


import io.github.mela.command.bind.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class ArrayMapperProviderTest extends BindingTest<ArrayMapperProviderTest.TestCommand> {

  protected ArrayMapperProviderTest() {
    super(TestCommand::new);
  }

  @Test
  void testArrayMapper() {
    String[] args = {"one", "two three", "four", "five"};
    dispatcher.dispatch("foo one \"two three\" four five", CommandContext.create());
    assertArrayEquals(args, command.values, "Arguments were not correctly mapped");
  }

  @Test
  void testArrayMapperScoped() {
    String[] args = {"six", "seven eight", "nine"};
    dispatcher.dispatch("foo [six \"seven eight\" nine]", CommandContext.create());
    assertArrayEquals(args, command.values, "Arguments were not mapped correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMapperProvider(new ArrayMapperProvider());
  }

  public static final class TestCommand {
    private String[] values;

    @Command(labels = "foo")
    public void execute(String... values) {
      this.values = values;
    }

  }

}