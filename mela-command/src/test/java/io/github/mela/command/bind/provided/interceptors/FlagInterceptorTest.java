package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlagInterceptorTest extends BindingTest<FlagInterceptorTest.TestCommand> {

  protected FlagInterceptorTest() {
    super(TestCommand::new);
  }

  @Test
  void testValueFlag() {
    dispatcher.dispatch("foo something -s value", CommandContext.create());
    assertEquals("value", command.value, "Flag value was not set correctly");
    dispatcher.dispatch("foo -s \"Lorem ipsum\" something", CommandContext.create());
    assertEquals("Lorem ipsum", command.value, "Flag value was not set correctly");
  }

  @Test
  void testBooleanFlag() {
    dispatcher.dispatch("bar something -f", CommandContext.create());
    assertTrue(command.presence, "Flag was not recognised");
    dispatcher.dispatch("bar something", CommandContext.create());
    assertFalse(command.presence, "Flag value was not set correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMapper(Boolean.class, (a, c) -> null)
        .bindMappingInterceptor(Flag.class, new FlagInterceptor());
  }

  public static final class TestCommand {
    private String value;
    private Boolean presence;

    @Command(labels = "foo")
    public void executeFoo(@Flag("s") String value, String arg) {
      this.value = value;
    }

    @Command(labels = "bar")
    public void executeBar(@Flag("f") Boolean presence, String arg) {
      this.presence = presence;
    }
  }

}