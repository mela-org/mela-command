package io.github.mela.command.bind.map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class InvalidArgumentAmountException extends RuntimeException {

  public InvalidArgumentAmountException() {
    super();
  }

  public InvalidArgumentAmountException(String message) {
    super(message);
  }

  public InvalidArgumentAmountException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidArgumentAmountException(Throwable cause) {
    super(cause);
  }

  protected InvalidArgumentAmountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
