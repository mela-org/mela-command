package io.github.mela.command.bind.provided.interceptors;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.bind.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

class RestInterceptorTest extends BindingTest<RestInterceptorTest.TestCommand> {

  protected RestInterceptorTest() {
    super(TestCommand::new);
  }

  @Test
  void testRestInterceptor() {
    dispatcher.dispatch("foo some more arguments \"that go\" here", CommandContext.create());
    assertEquals("some more arguments \\\"that go\\\" here", command.value,
        "Rest arguments were not mapped correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMappingInterceptor(Rest.class, new RestInterceptor());
  }

  public static final class TestCommand {
    private String value;

    @Command(labels = "foo")
    public void execute(@Rest String value) {
      this.value = value;
    }
  }

}