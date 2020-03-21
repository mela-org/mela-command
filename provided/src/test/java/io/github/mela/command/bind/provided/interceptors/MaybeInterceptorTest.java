package io.github.mela.command.bind.provided.interceptors;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.bind.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class MaybeInterceptorTest extends BindingTest<MaybeInterceptorTest.TestCommand> {

  protected MaybeInterceptorTest() {
    super(TestCommand::new);
  }

  @Test
  void testProvidedValue() {
    dispatcher.dispatch("foo value", CommandContext.create());
    assertEquals("value", command.value, "Argument was not mapped correctly");
  }

  @Test
  void testMissingValue() {
    dispatcher.dispatch("foo", CommandContext.create());
    assertEquals("null", command.value, "Value was not passed as null to the command");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMappingInterceptor(Maybe.class, new MaybeInterceptor());
  }

  public static final class TestCommand {
    private String value;

    @Command(labels = "foo")
    public void execute(@Maybe String value) {
      this.value = value == null ? "null" : value;
    }
  }

}