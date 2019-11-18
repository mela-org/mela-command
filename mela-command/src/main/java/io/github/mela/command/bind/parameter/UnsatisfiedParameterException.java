package io.github.mela.command.bind.parameter;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class UnsatisfiedParameterException extends RuntimeException {

  private final CommandParameter parameter;

  public UnsatisfiedParameterException(CommandParameter parameter) {
    super();
    this.parameter = parameter;
  }

  public UnsatisfiedParameterException(CommandParameter parameter, String message) {
    super(message);
    this.parameter = parameter;
  }

  public UnsatisfiedParameterException(CommandParameter parameter, String message, Throwable cause) {
    super(message, cause);
    this.parameter = parameter;
  }

  public UnsatisfiedParameterException(CommandParameter parameter, Throwable cause) {
    super(cause);
    this.parameter = parameter;
  }

  protected UnsatisfiedParameterException(CommandParameter parameter, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.parameter = parameter;
  }

  public CommandParameter getParameter() {
    return parameter;
  }
}
