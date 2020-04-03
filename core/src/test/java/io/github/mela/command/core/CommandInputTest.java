package io.github.mela.command.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandInputTest {

  private static final String ARGUMENTS = "baz   lorem\nipsum 81wadb";

  private CommandCallable command;
  private CommandGroup root;
  private CommandGroup child;

  @BeforeEach
  void setUp() {
    command = AssembledCommandCallable.builder().withLabels("bar").withAction((a, c) -> {
    }).build();
    root = ImmutableGroup.builder().group("foo").add(command).root().build();
    child = root.findChild("foo").orElseThrow(AssertionError::new);
  }

  @Test
  void testRawInput() {
    CommandInput input = CommandInput.parse(root, ARGUMENTS);
    assertEquals(ARGUMENTS, input.getRaw(), "Raw input was not preserved");
  }

  @Test
  void testFullInput() {
    CommandInput input = CommandInput.parse(root, "foo bar " + ARGUMENTS);
    assertEquals(child, input.getGroup(), "Group was not parsed correctly");
    assertTrue(input.getCommand().isPresent(), "Command was not recognised");
    assertSame(command, input.getCommand().get(), "Command was not parsed correctly");
    assertEquals(ARGUMENTS, input.getRemaining(), "Arguments were not parsed correctly");
  }

  @Test
  void testUnknownCommandInput() {
    CommandInput input = CommandInput.parse(root, "foo baz " + ARGUMENTS);
    assertEquals(child, input.getGroup(), "Group was not parsed correctly");
    assertFalse(input.getCommand().isPresent(), "Command was falsely recognised");
    assertEquals("baz " + ARGUMENTS, input.getRemaining(),
        "Arguments were not parsed correctly");
  }

  @Test
  void testUnknownGroupInput() {
    CommandInput input = CommandInput.parse(root, "bar foo " + ARGUMENTS);
    assertEquals(root, input.getGroup(), "Group was falsely recognised");
    assertFalse(input.getCommand().isPresent(), "Command was falsely recognised");
    assertEquals("bar foo " + ARGUMENTS, input.getRemaining(),
        "Arguments were not parsed correctly");
  }
}