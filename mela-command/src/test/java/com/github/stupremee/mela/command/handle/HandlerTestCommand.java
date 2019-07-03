package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.Command;
import com.github.stupremee.mela.command.TestException;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class HandlerTestCommand {

  @Command(aliases = "throw")
  public void execute() {
    throw new TestException();
  }

}
