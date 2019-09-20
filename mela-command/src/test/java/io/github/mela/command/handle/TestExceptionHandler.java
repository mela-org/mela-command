package io.github.mela.command.handle;

import io.github.mela.command.CommandContext;
import io.github.mela.command.TestException;
import io.github.mela.command.bind.ExceptionHandler;

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
