package io.github.mela.command.bind;

import io.github.mela.command.bind.map.MappingProcessException;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ArgumentValidationException extends MappingProcessException {

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

  protected ArgumentValidationException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
