package com.github.stupremee.mela.command.exception;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ConflictException extends RuntimeException {

  public ConflictException() {
    super();
  }

  public ConflictException(String message) {
    super(message);
  }

  public ConflictException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConflictException(Throwable cause) {
    super(cause);
  }

  protected ConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
