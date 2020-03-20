package io.github.mela.command.bind;

import io.github.mela.command.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class ArgumentMapperTest {

  private TestCommand command;
  private Dispatcher dispatcher;

  @BeforeEach
  void setUp() {
    command = new TestCommand();
    CommandBindings bindings = BindingsBuilder.create()
        .bindMapper(String.class, (a, c) -> a.nextString())
        .build();
    CommandGroup group = GroupBuilder.create()
        .withCommand(command)
        .compile(new MethodHandleCompiler(bindings));
    dispatcher = new DefaultDispatcher(group);
  }

  @Test
  void testArgumentMapping() {
    dispatcher.dispatch("foo \"Lorem ipsum\" solor dit-*oiut", CommandContext.create());
    assertEquals("Lorem ipsum", command.one, "Argument was not mapped correctly");
    assertEquals("solor", command.two, "Argument was not mapped correctly");
    assertEquals("dit-*oiut", command.three, "Argument was not mapped correctly");
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
