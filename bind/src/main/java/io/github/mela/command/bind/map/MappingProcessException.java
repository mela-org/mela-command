package io.github.mela.command.bind.map;

import static com.google.common.base.Preconditions.checkNotNull;


import io.github.mela.command.bind.parameter.CommandParameter;
import java.lang.reflect.Type;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MappingProcessException extends RuntimeException {

  private final Type type;
  private CommandParameter parameter;

  protected MappingProcessException(
      @Nonnull String message, @Nonnull Type type, @Nullable Throwable cause) {
    super(checkNotNull(message), cause);
    this.type = checkNotNull(type);
  }

  public static MappingProcessException create(
      @Nonnull String message, @Nonnull Type type) {
    return new MappingProcessException(message, type, null);
  }

  public static MappingProcessException create(
      @Nonnull String message, @Nonnull Type type, @Nullable Throwable cause) {
    return new MappingProcessException(message, type, cause);
  }

  @Nonnull
  public CommandParameter getParameter() {
    return parameter;
  }

  public void setParameter(@Nonnull CommandParameter parameter) {
    this.parameter = checkNotNull(parameter);
  }

  @Nonnull
  public Type getType() {
    return type;
  }
}
