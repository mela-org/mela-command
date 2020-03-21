package io.github.mela.command.provided.interceptors;

import io.github.mela.command.bind.map.MappingProcessException;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MissingContextException extends MappingProcessException {

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

  protected MissingContextException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
