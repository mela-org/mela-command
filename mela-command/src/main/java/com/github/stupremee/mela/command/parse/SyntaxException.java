package com.github.stupremee.mela.command.parse;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class SyntaxException extends RuntimeException {

  public SyntaxException() {
    super();
  }

  public SyntaxException(String message) {
    super(message);
  }

  public SyntaxException(String message, Throwable cause) {
    super(message, cause);
  }

  public SyntaxException(Throwable cause) {
    super(cause);
  }

  protected SyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
