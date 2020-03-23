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

  @Test
  void testGroupNameDuplicateReaction() {
    assertThrows(IllegalArgumentException.class,
        () -> ImmutableGroup.builder()
            .group("foo", "bar", "baz", "w")
            .parent()
            .group("lig", "wer", "bar")
            .parent().build(),
        "ImmutableGroup did not throw for duplicate group names");
  }

  @Test
  void testCommandLabelDuplicateReaction() {
    CommandCallable one = AssembledCommandCallable.builder()
        .withLabels("foo", "bar", "baz", "w")
        .withAction((a, c) -> {
        })
        .build();
    CommandCallable two = AssembledCommandCallable.builder()
        .withLabels("lig", "wer", "bar")
        .withAction((a, c) -> {
        })
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> ImmutableGroup.builder()
            .add(one)
            .add(two)
            .build(), "ImmutableGroup did not throw for duplicate command labels");
  }

  @Test
  void testEmptyGroupNamesReaction() {
    assertThrows(IllegalArgumentException.class,
        () -> ImmutableGroup.builder().group().parent().build(),
        "ImmutableGroup did not throw for empty group names");
  }

  @Test
  void testMultipleEmptyCommandLabelsReaction() {
    CommandCallable one = AssembledCommandCallable.builder()
        .withLabels()
        .withAction((a, c) -> {
        })
        .build();
    CommandCallable two = AssembledCommandCallable.builder()
        .withLabels()
        .withAction((a, c) -> {
        })
        .build();
    assertThrows(IllegalArgumentException.class,
        () -> ImmutableGroup.builder()
            .add(one)
            .add(two)
            .build(), "ImmutableGroup did not throw for multiple empty command labels");
  }
}