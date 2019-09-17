package com.github.stupremee.mela.command.util;

import com.github.stupremee.mela.command.core.AssembledCommandCallable;
import com.github.stupremee.mela.command.core.CommandCallable;
import com.github.stupremee.mela.command.core.CommandGroup;
import com.github.stupremee.mela.command.core.GroupBuilder;
import com.github.stupremee.mela.command.core.parse.CommandInput;
import com.github.stupremee.mela.command.core.parse.CommandInputParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandInputTest {

  private CommandGroup root;
  private CommandCallable command;

  @BeforeEach
  public void setUp() {
    command = AssembledCommandCallable.builder().withLabels("bar").build();
    root = GroupBuilder.create()
        .group("foo")
          .withCommand(command)
        .root()
        .compileIdentity();
  }

  @AfterEach
  public void nullify() {
    root = null;
    command = null;
  }

  @Test
  public void testCommandInputParsing() {
    CommandInput input = CommandInputParser.parse(root, "foo bar 123 baz");
    assertTrue(root.getChildren().contains(input.getGroup()), "Group was not parsed correctly");
    assertEquals(command, input.getCallable().get(), "Callable was not parsed correctly");
    assertEquals("123 baz", input.getArguments(), "Arguments were not parsed correctly");
  }


}
