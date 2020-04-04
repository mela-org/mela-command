package io.github.mela.command.bind.map;

import static com.google.common.base.Preconditions.checkNotNull;


import java.lang.reflect.Type;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ArgumentMappingException extends MappingProcessException {

  private final String argument;

  protected ArgumentMappingException(
      @Nonnull String message, @Nonnull Type type,
      @Nonnull String argument, @Nullable Throwable cause
  ) {
    super(message, type, cause);
    this.argument = checkNotNull(argument);
  }

  public static ArgumentMappingException create(
      @Nonnull String message, @Nonnull Type type,
      @Nonnull String argument, @Nullable Throwable cause
  ) {
    return new ArgumentMappingException(message, type, argument, cause);
  }

  public static ArgumentMappingException create(
      @Nonnull String message, Type type, @Nonnull String argument
  ) {
    return create(message, type, argument, null);
  }

  @Nonnull
  public String getArgument() {
    return argument;
  }
}
