package io.github.mela.command.bind;

<<<<<<< HEAD:mela-command/src/test/java/com/github/stupremee/mela/command/bind/BindingTest.java
import com.github.stupremee.mela.command.core.CommandGroup;
=======
import io.github.mela.command.CommandGroup;
>>>>>>> 128ccf988eb675eabc1b310c2c0da95b0e6d2ee8:mela-command/src/test/java/io/github/mela/command/bind/BindingTest.java
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class BindingTest {

  @Inject
  private CommandGroup root;

  @BeforeEach
  public void setUp() {
    Injector injector = Guice.createInjector(new BasicBindingTestModule(), new OverrideTestModule());
    injector.injectMembers(this);
  }

  @Test
  public void testCommandPresence() {
    Collection<?> commandObjects = root.getCommands();
    assertTrue(commandObjects.contains(BasicBindingTestModule.COMMAND),
        "Root does not contain command object from BasicBindingTestModule");
    assertTrue(commandObjects.contains(OverrideTestModule.ADDITIONAL_COMMAND),
        "Root does not contain command object from OverrideTestModule");
  }

}
