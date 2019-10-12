package io.github.mela.command.handle;

<<<<<<< HEAD:mela-command/src/test/java/com/github/stupremee/mela/command/handle/TestExceptionHandler.java
import com.github.stupremee.mela.command.core.CommandContext;
import com.github.stupremee.mela.command.TestException;
import com.github.stupremee.mela.command.bind.ExceptionHandler;
=======
import io.github.mela.command.CommandContext;
import io.github.mela.command.TestException;
import io.github.mela.command.bind.ExceptionHandler;
>>>>>>> 128ccf988eb675eabc1b310c2c0da95b0e6d2ee8:mela-command/src/test/java/io/github/mela/command/handle/TestExceptionHandler.java

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class TestExceptionHandler implements ExceptionHandler<TestException> {

  private boolean handled = false;

  @Override
  public void handle(@Nonnull Throwable exception, @Nonnull CommandContext context) {
    this.handled = true;
  }

  public boolean wasHandled() {
    return handled;
  }
}
