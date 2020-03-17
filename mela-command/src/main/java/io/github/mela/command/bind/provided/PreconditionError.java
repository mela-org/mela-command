package io.github.mela.command.bind.provided;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PreconditionError extends Error {

  public PreconditionError() {
    super();
  }

  public PreconditionError(String message) {
    super(message + "\nPlease resolve this error in your command method declaration.");
  }

  public PreconditionError(String message, Throwable cause) {
    super(message + "\nPlease resolve this error in your command method declaration.", cause);
  }

  public PreconditionError(Throwable cause) {
    super(cause);
  }

  protected PreconditionError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message + "\nPlease resolve this error in your command method declaration.", cause, enableSuppression, writableStackTrace);
  }
}
