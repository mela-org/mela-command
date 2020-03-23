package io.github.mela.command.provided.mappers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import io.github.mela.command.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class OptionalMapperProviderTest extends BindingTest<OptionalMapperProviderTest.TestCommand> {

  protected OptionalMapperProviderTest() {
    super(TestCommand::new);
  }

  @Test
  void testProvidedValue() {
    dispatcher.dispatch("option value", CommandContext.create());
    assertTrue(command.value.isPresent(), "Provided value was not present");
    assertEquals("value", command.value.get(), "Argument was not mapped correctly");
  }

  @Test
  void testMissingValue() {
    dispatcher.dispatch("option", CommandContext.create());
    assertFalse(command.value.isPresent(), "Value was present although not provided");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMapperProvider(new OptionalMapperProvider());
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  public static final class TestCommand {

    private Optional<String> value;

    @Command(labels = "option")
    public void execute(Optional<String> value) {
      this.value = value;
    }
  }

}