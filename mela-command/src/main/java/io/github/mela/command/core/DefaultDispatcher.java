package io.github.mela.command.core;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.mela.command.bind.guice.CommandExecutor;

import javax.annotation.Nonnull;
import java.util.concurrent.Executor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public final class DefaultDispatcher implements Dispatcher {

  private final CommandGroup root;
  private Executor executor;

  @Inject
  public DefaultDispatcher(@Nonnull CommandGroup root) {
    this.root = root;
    this.executor = Runnable::run;
  }

  @Inject(optional = true)
  public void setExecutor(@Nonnull @CommandExecutor Executor executor) {
    this.executor = checkNotNull(executor);
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
