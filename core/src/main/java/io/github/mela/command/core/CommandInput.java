package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandInput {

  private final String raw;
  private final CommandGroup group;
  private final CommandCallable command;
  private final String remaining;

  CommandInput(@Nonnull String raw, @Nonnull CommandGroup group,
               @Nullable CommandCallable command, @Nonnull String remaining) {
    this.raw = checkNotNull(raw);
    this.group = checkNotNull(group);
    this.command = command;
    this.remaining = checkNotNull(remaining);
  }

  @Nonnull
  @CheckReturnValue
  public static CommandInput parse(@Nonnull CommandGroup root, @Nonnull String input) {
    return new CommandInputParser(root, input).parse();
  }

  @Nonnull
  public String getRaw() {
    return raw;
  }

  @Nonnull
  public CommandGroup getGroup() {
    return group;
  }

  @Nullable
  public CommandCallable getCommand() {
    return command;
  }

  @Nonnull
  public String getRemaining() {
    return remaining;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandInput input = (CommandInput) o;
    return Objects.equals(group, input.group)
        && Objects.equals(command, input.command)
        && Objects.equals(remaining, input.remaining);
  }

  @Override
  public int hashCode() {
    return Objects.hash(group, command, remaining);
  }

  @Override
  public String toString() {
    return raw;
  }
}
