package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.inject.Inject;
import io.github.mela.command.guice.CommandExecutor;
import java.util.concurrent.Executor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultDispatcher implements CommandDispatcher {

  private final CommandGroup root;
  private final Executor executor;

  @Inject
  DefaultDispatcher(CommandGroup root, @Nullable @CommandExecutor Executor executor) {
    this.root = root;
    this.executor = executor == null ? Runnable::run : executor;
  }

  @Nonnull
  public static CommandDispatcher create(@Nonnull CommandGroup root) {
    return create(root, Runnable::run);
  }

  @Nonnull
  public static CommandDispatcher create(@Nonnull CommandGroup root, @Nonnull Executor executor) {
    return new DefaultDispatcher(checkNotNull(root), checkNotNull(executor));
  }

  @Override
  public boolean dispatch(@Nonnull String command, @Nonnull CommandContext context) {
    CommandInput input = CommandInput.parse(root, command);
    context.put(CommandInput.class, "input", input);
    CommandCallable callable = input.getCommand();
    if (callable != null) {
      CommandArguments arguments = CommandArguments.of(input.getRemaining());
      executor.execute(() -> callable.call(arguments, context));
      return true;
    } else {
      return false;
    }
  }

}
