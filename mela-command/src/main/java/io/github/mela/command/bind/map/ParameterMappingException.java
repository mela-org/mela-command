package io.github.mela.command.bind.map;

import io.github.mela.command.bind.parameter.CommandParameter;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ParameterMappingException extends MappingProcessException {

  private final CommandParameter parameter;

  public ParameterMappingException(@Nonnull CommandParameter parameter) {
    super();
    this.parameter = parameter;
  }

  public ParameterMappingException(String message, @Nonnull CommandParameter parameter) {
    super(message);
    this.parameter = parameter;
  }

  public ParameterMappingException(String message, Throwable cause, @Nonnull CommandParameter parameter) {
    super(message, cause);
    this.parameter = parameter;
  }

  public ParameterMappingException(Throwable cause, @Nonnull CommandParameter parameter) {
    super(cause);
    this.parameter = parameter;
  }

  protected ParameterMappingException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace, @Nonnull CommandParameter parameter) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.parameter = parameter;
  }

  @Nonnull
  public CommandParameter getParameter() {
    return parameter;
  }
}
