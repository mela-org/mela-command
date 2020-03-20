package io.github.mela.command.bind.guice;

import com.google.inject.Inject;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.DefaultDispatcher;
import io.github.mela.command.core.Dispatcher;

import javax.annotation.Nonnull;
import java.util.concurrent.Executor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultDispatcherProvider extends SingletonProvider<Dispatcher> {

  private final CommandGroup root;
  private Executor executor;

  @Inject
  public DefaultDispatcherProvider(CommandGroup root) {
    this.root = root;
    this.executor = Runnable::run;
  }

  @Override
  public Dispatcher create() {
    return DefaultDispatcher.create(root, executor);
  }

  @Inject(optional = true)
  public void setExecutor(@Nonnull @CommandExecutor Executor executor) {
    this.executor = checkNotNull(executor);
  }
}
