package io.github.mela.command.bind.map;

import io.github.mela.command.bind.parameter.CommandParameter;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ArgumentValidationException extends RuntimeException {

  private final CommandParameter parameter;

  public ArgumentValidationException(@Nonnull CommandParameter parameter) {
    super();
    this.parameter = checkNotNull(parameter);
  }

  public ArgumentValidationException(String message, @Nonnull CommandParameter parameter) {
    super(message);
    this.parameter = checkNotNull(parameter);
  }

  public ArgumentValidationException(String message, Throwable cause, @Nonnull CommandParameter parameter) {
    super(message, cause);
    this.parameter = checkNotNull(parameter);
  }

  public ArgumentValidationException(Throwable cause, @Nonnull CommandParameter parameter) {
    super(cause);
    this.parameter = checkNotNull(parameter);
  }

  protected ArgumentValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, @Nonnull CommandParameter parameter) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.parameter = checkNotNull(parameter);
  }

  @Nonnull
  public CommandParameter getParameter() {
    return parameter;
  }
}
