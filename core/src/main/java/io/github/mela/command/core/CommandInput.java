package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


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
  private final String arguments;

  CommandInput(@Nonnull String raw, @Nonnull CommandGroup group,
               @Nullable CommandCallable command, @Nonnull String arguments) {
    this.raw = checkNotNull(raw);
    this.group = checkNotNull(group);
    this.command = command;
    this.arguments = checkNotNull(arguments);
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
  public String getArguments() {
    return arguments;
  }

}
