package io.github.mela.command.bind.provided.mappers.providers;

import io.github.mela.command.bind.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapMapperProviderTest extends BindingTest<MapMapperProviderTest.TestCommand> {

  protected MapMapperProviderTest() {
    super(TestCommand::new);
  }

  @Test
  void testMapMapper() {
    dispatcher.dispatch("map one two three four five six seven", CommandContext.create());
    assertEquals(Map.of("one", "two", "four", "five", "six", "seven"), command.values,
        "Arguments were not mapped correctly");
  }

  @Test
  void testMapMapperScoped() {
    dispatcher.dispatch("map {one two three four five six seven}", CommandContext.create());
    assertEquals(Map.of("one", "two", "four", "five", "six", "seven"), command.values,
        "Arguments were not mapped correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMapperProvider(new MapMapperProvider<>(HashMap.class, HashMap::new));
  }

  public static final class TestCommand {
    private Map<String, String> values;

    @Command(labels = "map")
    public void execute(Map<String, String> values) {
      this.values = values;
    }
  }

}