package io.github.mela.command.bind.map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MissingMapperBindingError extends Error {

  public MissingMapperBindingError() {
    super();
  }

  public MissingMapperBindingError(String message) {
    super(message);
  }

  public MissingMapperBindingError(String message, Throwable cause) {
    super(message, cause);
  }

  public MissingMapperBindingError(Throwable cause) {
    super(cause);
  }

  protected MissingMapperBindingError(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
