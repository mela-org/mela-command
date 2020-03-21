package io.github.mela.command.bind.provided.mappers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.bind.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.Base;
import io.github.mela.command.bind.provided.Localized;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class NumberMapperProviderTest extends BindingTest<NumberMapperProviderTest.TestCommand> {

  protected NumberMapperProviderTest() {
    super(TestCommand::new);
  }

  @Test
  void testPlainInt() {
    dispatcher.dispatch("plainInt 57", CommandContext.create());
    assertEquals(57, command.plainInt, "Argument was not mapped correctly");
  }

  @Test
  void testBaseInt() {
    dispatcher.dispatch("baseInt 110101", CommandContext.create());
    assertEquals(0b110101, command.baseInt, "Argument was not mapped correctly");
  }

  @Test
  void testPlainDouble() {
    dispatcher.dispatch("plainDouble 3.141", CommandContext.create());
    assertEquals(3.141, command.plainDouble, "Argument was not mapped correctly");
  }

  @Test
  void testLocalizedInt() {
    dispatcher.dispatch("localizedInt 129,323,987", CommandContext.create());
    assertEquals(129_323_987, command.localizedInt, "Argument was not mapped correctly");
  }

  @Test
  void testLocalizedDouble() {
    dispatcher.dispatch("localizedDouble 1,892.0076", CommandContext.create());
    assertEquals(1_892.0076, command.localizedDouble, "Argument was not mapped correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder.bindMapperProvider(new NumberMapperProvider());
  }

  public static final class TestCommand {

    private int plainInt;
    private int baseInt;
    private double plainDouble;
    private int localizedInt;
    private double localizedDouble;

    @Command(labels = "plainInt")
    public void executePlainInt(int value) {
      plainInt = value;
    }

    @Command(labels = "baseInt")
    public void executeBaseInt(@Base(2) int value) {
      baseInt = value;
    }

    @Command(labels = "plainDouble")
    public void executePlainDouble(double value) {
      plainDouble = value;
    }

    @Command(labels = "localizedInt")
    public void executeLocalizedInt(@Localized int value) {
      localizedInt = value;
    }

    @Command(labels = "localizedDouble")
    public void executeLocalizedDouble(@Localized double value) {
      localizedDouble = value;
    }
  }

}