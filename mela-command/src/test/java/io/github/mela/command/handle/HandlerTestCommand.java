package io.github.mela.command.handle;

import io.github.mela.command.bind.Command;
import io.github.mela.command.TestException;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class HandlerTestCommand {

  @Command(aliases = "throw")
  public void execute() {
    throw new TestException();
  }

}
