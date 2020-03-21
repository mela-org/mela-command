package io.github.mela.command.bind.parameter;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.lang.reflect.Parameter;
import org.junit.jupiter.api.Test;

class CommandParameterTest {

  private static final String DESCRIPTION = "ABCDEFGH";

  @Test
  void testParameterParsing() throws NoSuchMethodException {
    Parameter parameter = this.getClass().getMethod("method", String.class).getParameters()[0];
    CommandParameter commandParameter = CommandParameter.of(parameter);
    assertEquals("object", commandParameter.getName(), "Name was not recognised");
    assertEquals(String.class, commandParameter.getTargetType().getType(),
        "Type was not recognised");
    assertEquals(DESCRIPTION, commandParameter.getDescription(), "Description was not recognised");
  }

  public void method(@Description(DESCRIPTION) String object) {
  }

}