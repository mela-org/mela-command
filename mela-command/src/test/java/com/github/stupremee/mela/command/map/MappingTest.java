package com.github.stupremee.mela.command.map;

import com.github.stupremee.mela.command.CommandTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingTest extends CommandTest<MappingTestCommand> {

  protected MappingTest() {
    super(MappingTestModule::new);
  }

  @Test
  public void testSimpleMapping() {
    callCommand("simple object");
    ObjectWrapping result = getSubject().getSimpleResult();
    assertNotNull(result, "Custom object argument was null");
    assertNotNull(result.getContent(), "Content of result was null");
  }

  @Test
  public void testGenericMapping() {
    callCommand("generic string");
    GenericWrapping<String> result = getSubject().getGenericResult();
    assertNotNull(result, "Custom generic argument was null");
    assertNotNull(result.getContent(), "Content of generic result was null");
  }
}
