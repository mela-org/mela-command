package io.github.mela.command.bind.provided;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ArgumentValidationException extends RuntimeException {

  public ArgumentValidationException() {
    super();
  }

  public ArgumentValidationException(String message) {
    super(message);
  }

  public ArgumentValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public ArgumentValidationException(Throwable cause) {
    super(cause);
  }

  protected ArgumentValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
