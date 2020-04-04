package io.github.mela.command.bind;

import io.github.mela.command.bind.map.MappingProcessException;
import java.lang.reflect.Type;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ArgumentValidationException extends MappingProcessException {

  private final Object value;

  protected ArgumentValidationException(
      @Nonnull String message, @Nonnull Type type,
      @Nullable Object value, @Nullable Throwable cause
  ) {
    super(message, type, cause);
    this.value = value;
  }

  public static ArgumentValidationException create(
      @Nonnull String message, @Nonnull Type type,
      @Nullable Object value, @Nullable Throwable cause
  ) {
    return new ArgumentValidationException(message, type, value, cause);
  }

  public static ArgumentValidationException create(
      @Nonnull String message, @Nonnull Type type,
      @Nullable Object value
  ) {
    return create(message, type, value, null);
  }

  @Nullable
  public Object getValue() {
    return value;
  }
}
