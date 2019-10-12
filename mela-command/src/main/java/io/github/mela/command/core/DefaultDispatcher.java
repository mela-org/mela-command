package io.github.mela.command.core;

import com.google.inject.Inject;
import io.github.mela.command.core.parse.CommandInput;
import io.github.mela.command.guice.annotation.CommandExecutor;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.Executor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
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
    Optional<CommandCallable> callable = input.getCallable();
    if (callable.isPresent()) {
      executor.execute(() -> callable.get().call(input.getArguments(), context));
      return true;
    } else {
      return false;
    }
  }

}
