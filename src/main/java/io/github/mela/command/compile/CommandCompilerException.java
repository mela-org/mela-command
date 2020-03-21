package io.github.mela.command.compile;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandCompilerException extends RuntimeException {

  public CommandCompilerException() {
    super();
  }

  public CommandCompilerException(String message) {
    super(message);
  }

  public CommandCompilerException(String message, Throwable cause) {
    super(message, cause);
  }

  public CommandCompilerException(Throwable cause) {
    super(cause);
  }

  protected CommandCompilerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
