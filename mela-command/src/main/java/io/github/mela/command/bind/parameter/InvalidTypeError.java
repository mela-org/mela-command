package io.github.mela.command.bind.parameter;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class InvalidTypeError extends Error {

  public InvalidTypeError() {
    super();
  }

  public InvalidTypeError(String message) {
    super(message);
  }

  public InvalidTypeError(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidTypeError(Throwable cause) {
    super(cause);
  }

  protected InvalidTypeError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
