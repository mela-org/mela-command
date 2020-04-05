package io.github.mela.command.provided.mappers.providers;

import static org.junit.jupiter.api.Assertions.assertSame;


import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.provided.BindingTest;
import org.junit.jupiter.api.Test;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class EnumMapperProviderTest extends BindingTest<EnumMapperProviderTest.TestCommand> {

  protected EnumMapperProviderTest() {
    super(TestCommand::new);
  }

  @Test
  void testEnumMapperProvider() {
    dispatcher.dispatch("foo TWO", CommandContext.create());
    assertSame(TestEnum.TWO, command.value);
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapperProvider(new EnumMapperProvider());
  }

  public static final class TestCommand {

    private TestEnum value;

    @Command(labels = "foo")
    public void execute(TestEnum value) {
      this.value = value;
    }

  }

  private enum TestEnum {
    ONE, TWO, THREE
  }

}
