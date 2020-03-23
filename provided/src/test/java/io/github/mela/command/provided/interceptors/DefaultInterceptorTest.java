package io.github.mela.command.provided.interceptors;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class DefaultInterceptorTest extends BindingTest<DefaultInterceptorTest.TestCommand> {

  protected DefaultInterceptorTest() {
    super(TestCommand::new);
  }

  @Test
  void testProvidedValue() {
    dispatcher.dispatch("foo value", CommandContext.create());
    assertEquals("value", command.value);
  }

  @Test
  void testDefaultValue() {
    dispatcher.dispatch("foo", CommandContext.create());
    assertEquals("default", command.value);
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMappingInterceptor(Default.class, new DefaultInterceptor());
  }

  public static final class TestCommand {
    private String value;

    @Command(labels = "foo")
    public void execute(@Default("default") String value) {
      this.value = value;
    }
  }

}