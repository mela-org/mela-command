package io.github.mela.command.provided.interceptors;

import static org.junit.jupiter.api.Assertions.assertThrows;


import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.provided.BindingTest;
import io.github.mela.command.provided.mappers.StringMapper;
import org.junit.jupiter.api.Test;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CharsetInterceptorTest extends BindingTest<CharsetInterceptorTest.TestCommand> {


  protected CharsetInterceptorTest() {
    super(TestCommand::new);
  }

  @Test
  void testValidArgument() {
    dispatcher.dispatch("foo ABCbewfh712821hdwq-_", CommandContext.create());
  }

  @Test
  void testInvalidArgument() {
    assertThrows(RuntimeException.class,
        () -> dispatcher.dispatch("foo ABCbewfh7ü12821hdwq-_", CommandContext.create()),
        "CharsetInterceptor did not throw for invalid string");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMappingInterceptor(Charset.class, new CharsetInterceptor());
  }

  public static final class TestCommand {

    @Command(labels = "foo")
    public void execute(@Charset("US-ASCII") String value) {

    }
  }

}
