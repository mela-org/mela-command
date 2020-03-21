package io.github.mela.command.bind.provided.interceptors;

import static org.junit.jupiter.api.Assertions.assertThrows;


import io.github.mela.command.bind.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class MatchInterceptorTest extends BindingTest<MatchInterceptorTest.TestCommand> {

  protected MatchInterceptorTest() {
    super(TestCommand::new);
  }

  @Test
  void testValidArgument() {
    dispatcher.dispatch("foo \"hello, world\"", CommandContext.create());
  }

  @Test
  void testInvalidArgument() {
    assertThrows(RuntimeException.class,
        () -> dispatcher.dispatch("foo \"Fello, world!\"", CommandContext.create()),
        "Match interceptor did not throw for invalid argument");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMappingInterceptor(Match.class, new MatchInterceptor());
  }

  public static final class TestCommand {

    @Command(labels = "foo")
    public void execute(@Match("[Hh]ello,? world!?") String arg) {

    }
  }

}