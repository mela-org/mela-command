package io.github.mela.command.bind.parameter;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class InvalidParameterException extends RuntimeException {

  public InvalidParameterException() {
    super();
  }

  public InvalidParameterException(String message) {
    super(message);
  }

  public InvalidParameterException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidParameterException(Throwable cause) {
    super(cause);
  }

  protected InvalidParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
