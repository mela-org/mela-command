package io.github.mela.command.core;

import javax.annotation.Nonnull;
import java.util.concurrent.Executor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultDispatcher implements Dispatcher {

  private final CommandGroup root;
  private Executor executor;

  private DefaultDispatcher(CommandGroup root, Executor executor) {
    this.root = root;
    this.executor = executor;
  }

  public static DefaultDispatcher create(@Nonnull CommandGroup root) {
    return create(root, Runnable::run);
  }

  public static DefaultDispatcher create(@Nonnull CommandGroup root, @Nonnull Executor executor) {
    return new DefaultDispatcher(checkNotNull(root), checkNotNull(executor));
  }

  @Override
  public boolean dispatch(@Nonnull String command, @Nonnull CommandContext context) {
    CommandInput input = CommandInput.parse(root, command);
    CommandCallable callable = input.getCommand();
    if (callable != null) {
      executor.execute(() -> callable.call(input.getArguments(), context));
      return true;
    } else {
      return false;
    }
  }

}
