package io.github.mela.command.bind.map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class UnsatisfiedValueException extends MappingProcessException {

  public UnsatisfiedValueException() {
    super();
  }

  public UnsatisfiedValueException(String message) {
    super(message);
  }

  public UnsatisfiedValueException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnsatisfiedValueException(Throwable cause) {
    super(cause);
  }

  protected UnsatisfiedValueException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
