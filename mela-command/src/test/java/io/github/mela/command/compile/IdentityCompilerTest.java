package io.github.mela.command.compile;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityCompilerTest {

  @Test
  void testDifferentCommandTypeRejection() {
    assertThrows(CommandCompilerException.class, () -> IdentityCompiler.INSTANCE.compile(new Object()),
        "IdentityCompiler does not recognise invalid Objects");
  }
}