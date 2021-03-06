package io.github.mela.command.core;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandGroupTest {

  private CommandCallable defaultCommand;
  private CommandCallable barCommand;
  private CommandGroup root;
  private CommandGroup child;

  @BeforeEach
  void setUp() {
    defaultCommand = AssembledCommandCallable.builder().withAction((a, c) -> {
    }).build();
    barCommand = AssembledCommandCallable.builder().withLabels("bar").withAction((a, c) -> {
    }).build();
    root = ImmutableGroup.builder()
        .add(barCommand)
        .add(defaultCommand)
        .group("foo")
        .root()
        .build();
    child = root.findChild("foo").orElseThrow(AssertionError::new);
  }

  @Test
  void testFindChild() {
    assertEquals(child, root.findChild("foo").orElseThrow(AssertionError::new),
        "Correct child was not found");
  }

  @Test
  void testFindCommand() {
    assertEquals(barCommand, root.findCommand("bar").orElseThrow(AssertionError::new),
        "Correct command was not found");
  }

  @Test
  void testFindDefaultCommand() {
    assertEquals(defaultCommand, root.findDefaultCommand().orElseThrow(AssertionError::new),
        "Correct command was not found");
  }
}