package io.github.mela.command.provided.interceptors;

import static org.junit.jupiter.api.Assertions.assertThrows;


import io.github.mela.command.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.provided.mappers.SimpleNumberMapper;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class RangeInterceptorTest extends BindingTest<RangeInterceptorTest.TestCommand> {

  protected RangeInterceptorTest() {
    super(TestCommand::new);
  }

  @Test
  void testValidArgument() {
    dispatcher.dispatch("foo 48", CommandContext.create());
  }

  @Test
  void testInvalidArgument() {
    assertThrows(RuntimeException.class, () -> dispatcher.dispatch("foo 19", CommandContext.create()),
        "Range interceptor did not throw for out of range value");
    assertThrows(RuntimeException.class, () -> dispatcher.dispatch("foo 143", CommandContext.create()),
        "Range interceptor did not throw for out of range value");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(int.class, new SimpleNumberMapper<>(int.class, Integer::valueOf))
        .bindMappingInterceptor(Range.class, new RangeInterceptor());
  }

  public static final class TestCommand {

    @Command(labels = "foo")
    public void execute(@Range(from = 20, to = 143) int arg) {

    }
  }

}