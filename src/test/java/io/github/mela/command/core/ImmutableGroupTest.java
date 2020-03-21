package io.github.mela.command.core;

import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImmutableGroupTest {

  private CommandGroup group;

  @BeforeEach
  void setUp() {
    group = ImmutableGroup.EMPTY;
  }

  @Test
  void testNameImmutability() {
    assertThrows(UnsupportedOperationException.class, () -> group.getNames().clear(),
        "names set is not immutable");
  }

  @Test
  void testCommandImmutability() {
    assertThrows(UnsupportedOperationException.class, () -> group.getCommands().clear(),
        "commands set is not immutable");
  }

  @Test
  void testChildImmutability() {
    assertThrows(UnsupportedOperationException.class, () -> group.getChildren().clear(),
        "children set is not immutable");
  }

}