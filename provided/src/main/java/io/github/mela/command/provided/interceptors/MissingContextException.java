package io.github.mela.command.provided.interceptors;

import io.github.mela.command.bind.map.MappingProcessException;
import java.lang.reflect.Type;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MissingContextException extends MappingProcessException {

  private final String key;

  private MissingContextException(
      @Nonnull String message, @Nonnull Type type,
      @Nonnull String key, @Nullable Throwable cause
  ) {
    super(message, type, cause);
    this.key = key;
  }

  public static MissingContextException create(
      @Nonnull String message, @Nonnull Type type, @Nonnull String key
  ) {
    return create(message, type, key, null);
  }

  public static MissingContextException create(
      @Nonnull String message, @Nonnull Type type,
      @Nonnull String key, @Nullable Throwable cause
  ) {
    return new MissingContextException(message, type, key, cause);
  }

  @Nonnull
  public String getKey() {
    return key;
  }
}
