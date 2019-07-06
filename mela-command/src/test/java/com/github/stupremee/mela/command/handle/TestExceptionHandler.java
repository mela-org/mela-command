package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.TestException;

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
