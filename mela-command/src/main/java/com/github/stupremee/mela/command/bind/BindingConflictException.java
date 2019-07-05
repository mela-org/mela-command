package com.github.stupremee.mela.command.bind;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class BindingConflictException extends RuntimeException {

  public BindingConflictException() {
    super();
  }

  public BindingConflictException(String message) {
    super(message);
  }

  public BindingConflictException(String message, Throwable cause) {
    super(message, cause);
  }

  public BindingConflictException(Throwable cause) {
    super(cause);
  }

  protected BindingConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
