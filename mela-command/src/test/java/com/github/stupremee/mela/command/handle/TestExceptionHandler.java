package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.TestException;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class TestExceptionHandler implements ExceptionHandler<TestException> {

  private boolean handled = false;

  @Override
  public void handle(Throwable exception, CommandContext context) {
    this.handled = true;
  }

  public boolean wasHandled() {
    return handled;
  }
}