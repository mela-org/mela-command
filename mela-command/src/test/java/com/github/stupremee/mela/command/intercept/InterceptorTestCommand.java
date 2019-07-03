package com.github.stupremee.mela.command.intercept;

import com.github.stupremee.mela.command.Command;
import com.github.stupremee.mela.command.TestAnnotation;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InterceptorTestCommand {

  private boolean executed = false;

  @Command(aliases = "intercept")
  @TestAnnotation
  public void executeIntercepted() {
    this.executed = true;
  }

  @Command(aliases = "nointercept")
  public void execute() {
    this.executed = true;
  }

  public boolean isExecuted() {
    return executed;
  }
}
