package io.github.mela.command.bind.provided.interceptors;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MissingContextException extends RuntimeException {

  public MissingContextException() {
    super();
  }

  public MissingContextException(String message) {
    super(message);
  }

  public MissingContextException(String message, Throwable cause) {
    super(message, cause);
  }

  public MissingContextException(Throwable cause) {
    super(cause);
  }

  protected MissingContextException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
