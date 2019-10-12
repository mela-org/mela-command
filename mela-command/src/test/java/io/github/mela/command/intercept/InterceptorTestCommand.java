package io.github.mela.command.intercept;

import io.github.mela.command.bind.Command;
import io.github.mela.command.TestAnnotation;

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
