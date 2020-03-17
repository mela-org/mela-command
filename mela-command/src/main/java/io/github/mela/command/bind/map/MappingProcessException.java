package io.github.mela.command.bind.map;

import io.github.mela.command.bind.parameter.CommandParameter;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MappingProcessException extends RuntimeException {

  private CommandParameter parameter;

  public MappingProcessException() {
    super();
  }

  public MappingProcessException(String message) {
    super(message);
  }

  public MappingProcessException(String message, Throwable cause) {
    super(message, cause);
  }

  public MappingProcessException(Throwable cause) {
    super(cause);
  }

  protected MappingProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public CommandParameter getParameter() {
    return parameter;
  }

  public void setParameter(CommandParameter parameter) {
    this.parameter = parameter;
  }
}
