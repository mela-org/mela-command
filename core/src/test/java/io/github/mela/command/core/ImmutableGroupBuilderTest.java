package io.github.mela.command.core;

import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.Test;

class ImmutableGroupBuilderTest {

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Test
  void testDuplicateNameRecognition() {
    assertThrows(IllegalArgumentException.class,
        () -> ImmutableGroup.builder().group("foo").parent().group("foo").root().build(),
        "duplicate group name was not prevented");
  }

  @Test
  void testRootNode() {
    assertThrows(IllegalStateException.class, () -> ImmutableGroup.builder().parent(),
        "going to parent from root was not met with an exception");
  }
}