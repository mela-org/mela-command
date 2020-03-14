package io.github.mela.command.bind.map;

import io.github.mela.command.bind.parameter.CommandParameter;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ParameterMappingException extends MappingProcessException {

  private final CommandParameter parameter;

  public ParameterMappingException(CommandParameter parameter) {
    super();
    this.parameter = parameter;
  }

  public ParameterMappingException(String message, CommandParameter parameter) {
    super(message);
    this.parameter = parameter;
  }

  public ParameterMappingException(String message, Throwable cause, CommandParameter parameter) {
    super(message, cause);
    this.parameter = parameter;
  }

  public ParameterMappingException(Throwable cause, CommandParameter parameter) {
    super(cause);
    this.parameter = parameter;
  }

  protected ParameterMappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, CommandParameter parameter) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.parameter = parameter;
  }

  public CommandParameter getParameter() {
    return parameter;
  }
}
