package io.github.mela.command.map;

import io.github.mela.command.CommandContext;
import io.github.mela.command.Dispatcher;
import io.github.mela.command.SingleSubjectTest;
import com.google.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingTest extends SingleSubjectTest<MappingTestCommand> {

  @Inject
  private Dispatcher dispatcher;

  protected MappingTest() {
    super(MappingTestModule::new);
  }

  @Test
  public void testSimpleMapping() {
    dispatcher.dispatch("simple object", CommandContext.create());
    ObjectWrapping result = getSubject().getSimpleResult();
    assertNotNull(result, "Custom object argument was null");
    assertNotNull(result.getContent(), "Content of result was null");
  }

  @Test
  public void testGenericMapping() {
    dispatcher.dispatch("generic string", CommandContext.create());
    GenericWrapping<String> result = getSubject().getGenericResult();
    assertNotNull(result, "Custom generic argument was null");
    assertNotNull(result.getContent(), "Content of generic result was null");
  }
}
