package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ArgumentException extends RuntimeException {

  private final String originalArguments;
  private final String remainingArguments;
  private final int cursor;

  protected ArgumentException(String message, CommandArguments arguments, Throwable cause) {
    super(message, cause);
    this.originalArguments = arguments.getRaw();
    this.remainingArguments = arguments.toString();
    this.cursor = arguments.getRawCursor();
  }

  @Nonnull
  public static ArgumentException create(
      @Nonnull String message,
      @Nonnull CommandArguments arguments,
      @Nullable Throwable cause
  ) {
    return new ArgumentException(checkNotNull(message), checkNotNull(arguments), cause);
  }

  @Nonnull
  public static ArgumentException create(
      @Nonnull String message, @Nonnull CommandArguments arguments) {
    return create(message, arguments, null);
  }

  public String getOriginalArguments() {
    return originalArguments;
  }

  public String getRemainingArguments() {
    return remainingArguments;
  }

  public int getCursor() {
    return cursor;
  }
}
