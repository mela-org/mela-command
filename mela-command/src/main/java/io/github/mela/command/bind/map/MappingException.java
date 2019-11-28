package io.github.mela.command.bind.map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MappingException extends RuntimeException {

  public MappingException() {
    super();
  }

  public MappingException(String message) {
    super(message);
  }

  public MappingException(String message, Throwable cause) {
    super(message, cause);
  }

  public MappingException(Throwable cause) {
    super(cause);
  }

  protected MappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
