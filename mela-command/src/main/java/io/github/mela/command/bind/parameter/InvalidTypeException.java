package io.github.mela.command.bind.parameter;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class InvalidTypeException extends RuntimeException {

  public InvalidTypeException() {
    super();
  }

  public InvalidTypeException(String message) {
    super(message);
  }

  public InvalidTypeException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidTypeException(Throwable cause) {
    super(cause);
  }

  protected InvalidTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
