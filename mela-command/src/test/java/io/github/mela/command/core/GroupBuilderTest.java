package io.github.mela.command.core;

import io.github.mela.command.compile.IdentityCompiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupBuilderTest {

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @Test
  void testDuplicateNameRecognition() {
    assertThrows(IllegalArgumentException.class,
        () -> GroupBuilder.create().group("foo").parent().group("foo").root().compile(IdentityCompiler.INSTANCE),
        "duplicate group name was not prevented");
  }

  @Test
  void testRootNode() {
    assertThrows(IllegalStateException.class, () -> GroupBuilder.create().parent(),
        "going to parent from root was not met with an exception");
  }
}