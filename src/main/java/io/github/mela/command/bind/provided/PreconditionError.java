package io.github.mela.command.bind.provided;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PreconditionError extends Error {

  public static final String APPENDIX =
      "\nPlease resolve this error in your command method declaration.";

  public PreconditionError() {
    super();
  }

  public PreconditionError(String message) {
    super(message + APPENDIX);
  }

  public PreconditionError(String message, Throwable cause) {
    super(message + APPENDIX, cause);
  }

  public PreconditionError(Throwable cause) {
    super(cause);
  }

  protected PreconditionError(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message + APPENDIX, cause, enableSuppression, writableStackTrace);
  }
}
