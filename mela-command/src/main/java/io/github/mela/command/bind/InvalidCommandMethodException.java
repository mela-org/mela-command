package io.github.mela.command.bind;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class InvalidCommandMethodException extends RuntimeException {

  public InvalidCommandMethodException() {
    super();
  }

  public InvalidCommandMethodException(String message) {
    super(message);
  }

  public InvalidCommandMethodException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidCommandMethodException(Throwable cause) {
    super(cause);
  }

  protected InvalidCommandMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
