package io.github.mela.command.bind.provided.mappers.providers;

import static org.junit.jupiter.api.Assertions.assertEquals;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.github.mela.command.bind.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CollectionMapperProviderTest extends BindingTest<CollectionMapperProviderTest.TestCommand> {

  protected CollectionMapperProviderTest() {
    super(TestCommand::new);
  }

  @Test
  void testSetMapper() {
    dispatcher.dispatch("set one two three", CommandContext.create());
    assertEquals(ImmutableSet.of("one", "two", "three"), command.value,
        "Arguments were not mapped correctly");
  }

  @Test
  void testSetMapperScoped() {
    dispatcher.dispatch("set [four five six]", CommandContext.create());
    assertEquals(ImmutableSet.of("four", "five", "six"), command.value,
        "Arguments were not mapped correctly");
  }

  @Test
  void testListMapper() {
    dispatcher.dispatch("list one two three", CommandContext.create());
    assertEquals(ImmutableList.of("one", "two", "three"), command.value,
        "Arguments were not mapped correctly");
  }

  @Test
  void testListMapperScoped() {
    dispatcher.dispatch("list [four five six]", CommandContext.create());
    assertEquals(ImmutableList.of("four", "five", "six"), command.value,
        "Arguments were not mapped correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMapperProvider(new CollectionMapperProvider<>(LinkedHashSet.class, LinkedHashSet::new))
        .bindMapperProvider(new CollectionMapperProvider<>(ArrayList.class, ArrayList::new));
  }

  public static final class TestCommand {
    private Collection<String> value;

    @Command(labels = "set")
    public void executeSet(Set<String> value) {
      this.value = value;
    }

    @Command(labels = "list")
    public void executeList(List<String> value) {
      this.value = value;
    }
  }

}