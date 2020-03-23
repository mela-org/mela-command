package io.github.mela.command.provided.mappers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.google.common.collect.ImmutableMap;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.provided.BindingTest;
import io.github.mela.command.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MapMapperProviderTest extends BindingTest<MapMapperProviderTest.TestCommand> {

  protected MapMapperProviderTest() {
    super(TestCommand::new);
  }

  @Test
  void testMapMapper() {
    dispatcher.dispatch("map one two three four five six", CommandContext.create());
    assertEquals(ImmutableMap.of("one", "two", "three", "four", "five", "six"),
        command.values, "Arguments were not mapped correctly");
  }

  @Test
  void testMapMapperScoped() {
    dispatcher.dispatch("map {one two three four five six}", CommandContext.create());
    assertEquals(ImmutableMap.of("one", "two", "three", "four", "five", "six"),
        command.values, "Arguments were not mapped correctly");
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