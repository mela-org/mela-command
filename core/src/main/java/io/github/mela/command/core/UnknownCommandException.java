package io.github.mela.command.core;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class UnknownCommandException extends RuntimeException {

  public UnknownCommandException() {
  }

  public UnknownCommandException(String message) {
    super(message);
  }

  public UnknownCommandException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnknownCommandException(Throwable cause) {
    super(cause);
  }

  public UnknownCommandException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
