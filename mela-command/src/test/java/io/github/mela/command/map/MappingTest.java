package io.github.mela.command.map;

<<<<<<< HEAD:mela-command/src/test/java/com/github/stupremee/mela/command/map/MappingTest.java
import com.github.stupremee.mela.command.core.CommandContext;
import com.github.stupremee.mela.command.core.Dispatcher;
import com.github.stupremee.mela.command.SingleSubjectTest;
=======
import io.github.mela.command.CommandContext;
import io.github.mela.command.Dispatcher;
import io.github.mela.command.SingleSubjectTest;
>>>>>>> 128ccf988eb675eabc1b310c2c0da95b0e6d2ee8:mela-command/src/test/java/io/github/mela/command/map/MappingTest.java
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
