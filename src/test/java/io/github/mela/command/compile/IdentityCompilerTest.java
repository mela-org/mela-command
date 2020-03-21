package io.github.mela.command.compile;

import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.Test;

class IdentityCompilerTest {

  @Test
  void testDifferentCommandTypeRejection() {
    assertThrows(CommandCompilerException.class, () -> IdentityCompiler.INSTANCE.compile(new Object()),
        "IdentityCompiler does not recognise invalid Objects");
  }
}