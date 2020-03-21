package io.github.mela.command.bind.map;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.bind.BindingTest;
import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.core.CommandContext;
import org.junit.jupiter.api.Test;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class ArgumentMapperTest extends BindingTest<ArgumentMapperTest.TestCommand> {

  protected ArgumentMapperTest() {
    super(TestCommand::new);
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    return builder.bindMapper(String.class, (a, c) -> a.nextString());
  }

  @Test
  void testArgumentMapping() {
    dispatcher.dispatch("foo \"Lorem ipsum\" something e12§lew+*", CommandContext.create());
    assertEquals("Lorem ipsum", command.one, "Argument was not mapped correctly");
    assertEquals("something", command.two, "Argument was not mapped correctly");
    assertEquals("e12§lew+*", command.three, "Argument was not mapped correctly");
  }

  public static final class TestCommand {
    private String one, two, three;

    @Command(labels = "foo")
    public void execute(String one, String two, String three) {
      this.one = one;
      this.two = two;
      this.three = three;
    }
  }

}
