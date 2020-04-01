package io.github.mela.command.provided.interceptors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


import io.github.mela.command.provided.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.provided.mappers.StringMapper;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.provided.mappers.providers.NeverReachMapperProvider;
import org.junit.jupiter.api.Test;

class ContextInterceptorTest extends BindingTest<ContextInterceptorTest.TestCommand> {

  protected ContextInterceptorTest() {
    super(TestCommand::new);
  }

  @Test
  void testContextInterceptor() {
    CommandContext context = CommandContext.create();
    context.put(String.class, "key", "value");
    Object other = new Object();
    context.put(Object.class, "other", other);
    dispatcher.dispatch("foo", context);
    assertEquals("value", command.value, "Context value was not mapped");
    assertSame(other, command.other, "Context value was not mapped correctly");
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder
        .bindMapper(String.class, new StringMapper())
        .bindMappingInterceptor(Context.class, new ContextInterceptor())
        .bindMapperProvider(new NeverReachMapperProvider((type) -> type.getAnnotatedType().isAnnotationPresent(Context.class)));
  }

  public static final class TestCommand {
    private String value;
    private Object other;

    @Command(labels = "foo")
    public void execute(@Context("key") String value, @Context("other") Object object) {
      this.value = value;
      this.other = object;
    }
  }

}