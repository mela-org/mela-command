package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.mela.command.guice.CommandExecutor;
import io.github.mela.command.guice.StringDelimiter;
import java.util.concurrent.Executor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public final class DefaultDispatcher implements CommandDispatcher {

  private final CommandGroup root;
  private final Executor executor;
  private final char stringDelimiter;

  @Inject
  DefaultDispatcher(
      CommandGroup root,
      @Nullable @StringDelimiter Character stringDelimiter,
      @Nullable @CommandExecutor Executor executor
  ) {
    this.root = root;
    this.stringDelimiter = stringDelimiter == null
        ? CommandArguments.DEFAULT_STRING_DELIMITER : stringDelimiter;
    this.executor = executor == null
        ? Runnable::run : executor;
  }

  @Nonnull
  public static CommandDispatcher create(@Nonnull CommandGroup root) {
    return new DefaultDispatcher(checkNotNull(root), null, null);
  }

  @Nonnull
  public static CommandDispatcher create(@Nonnull CommandGroup root, char stringDelimiter) {
    return new DefaultDispatcher(checkNotNull(root), stringDelimiter, null);
  }

  @Nonnull
  public static CommandDispatcher create(@Nonnull CommandGroup root, @Nonnull Executor executor) {
    return new DefaultDispatcher(checkNotNull(root), null, checkNotNull(executor));
  }

  @Nonnull
  public static CommandDispatcher create(
      @Nonnull CommandGroup root, char stringDelimiter, @Nonnull Executor executor) {
    return new DefaultDispatcher(checkNotNull(root), stringDelimiter, checkNotNull(executor));
  }

  @Override
  public void dispatch(@Nonnull String command, @Nonnull CommandContext context) {
    CommandInput input = CommandInput.parse(root, command);
    context.put(CommandInput.class, "input", input);
    CommandCallable callable = input.getCommand()
        .orElseThrow(() -> new UnknownCommandException(
            "Could not find command for input \"" + input + "\""
        ));
    CommandArguments arguments = CommandArguments.of(input.getRemaining(), stringDelimiter);
    executor.execute(() -> callable.call(arguments, context));
  }

}
