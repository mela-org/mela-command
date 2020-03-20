package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RawStringMapperTest extends BindingTest<RawStringMapperTest.TestCommand> {

  protected RawStringMapperTest() {
    super(TestCommand::new);
  }

  @Test
  void testRawStringMapper() {
    dispatcher.dispatch("foo", CommandContext.create());
    assertEquals("", command.raw, "Raw string was not mapped");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder.bindMapper(String.class, Raw.class, new RawStringMapper());
  }

  public static final class TestCommand {

    private String raw;

    @Command(labels = "foo")
    public void execute(@Raw String raw) {
      this.raw = raw;
    }

  }

}