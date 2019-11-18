package io.github.mela.command.bind.map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MissingArgumentException extends InvalidArgumentAmountException {

  public MissingArgumentException() {
    super();
  }

  public MissingArgumentException(String message) {
    super(message);
  }

  public MissingArgumentException(String message, Throwable cause) {
    super(message, cause);
  }

  public MissingArgumentException(Throwable cause) {
    super(cause);
  }

  protected MissingArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
