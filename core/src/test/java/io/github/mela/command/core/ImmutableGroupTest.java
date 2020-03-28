package io.github.mela.command.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

  @Test
  void testMerge() {
    CommandCallable first = AssembledCommandCallable.builder()
        .withLabels("first")
        .withAction((a, c) -> {})
        .build();
    CommandCallable second = AssembledCommandCallable.builder()
        .withLabels("second")
        .withAction((a, c) -> {})
        .build();
    CommandCallable third = AssembledCommandCallable.builder()
        .withLabels("third")
        .withAction((a, c) -> {})
        .build();
    CommandGroup one = ImmutableGroup.builder()
        .group("foo")
          .add(first)
          .group("bar")
          .parent()
        .parent()
        .build();
    CommandGroup two = ImmutableGroup.builder()
        .group("foo")
          .add(second)
        .parent()
        .group("baz")
        .parent()
        .add(third)
        .build();
    CommandGroup expected = ImmutableGroup.builder()
        .group("foo")
          .add(first)
          .add(second)
          .group("bar")
          .parent()
        .parent()
        .group("baz")
        .parent()
        .add(third)
        .build();
    CommandGroup actual = ImmutableGroup.merge(one, two);
    assertEquals(expected, actual, "Groups were not merged correctly");
  }
}