package io.github.mela.command.example.bind;

import io.github.mela.command.bind.ExceptionHandler;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PrintExceptionHandler<T extends Throwable> implements ExceptionHandler<T> {

  private final String message;

  public PrintExceptionHandler(String message) {
    this.message = message;
  }

  @Override
  public void handle(@Nonnull T exception, @Nonnull CommandContext context) {
    System.out.printf("%s: %s%nUse \"help <command>\" for help.%n", message, exception);
  }
}
