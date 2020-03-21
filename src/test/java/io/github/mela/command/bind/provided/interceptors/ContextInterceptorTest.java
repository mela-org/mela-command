package io.github.mela.command.bind.provided.interceptors;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.bind.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class ContextInterceptorTest extends BindingTest<ContextInterceptorTest.TestCommand> {

  protected ContextInterceptorTest() {
    super(TestCommand::new);
  }

  @Test
  void testContextInterceptor() {
    CommandContext context = CommandContext.create();
    context.put(String.class, "key", "value");
    dispatcher.dispatch("foo", context);
    assertEquals("value", command.value, "Context value was not mapped");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMappingInterceptor(Context.class, new ContextInterceptor());
  }

  public static final class TestCommand {
    private String value;

    @Command(labels = "foo")
    public void execute(@Context("key") String value) {
      this.value = value;
    }
  }

}