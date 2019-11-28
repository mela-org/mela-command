package io.github.mela.command.bind.provided.interceptors;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class OutOfRangeException extends RuntimeException {

  public OutOfRangeException() {
    super();
  }

  public OutOfRangeException(String message) {
    super(message);
  }

  public OutOfRangeException(String message, Throwable cause) {
    super(message, cause);
  }

  public OutOfRangeException(Throwable cause) {
    super(cause);
  }

  protected OutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
