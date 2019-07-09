package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.util.CommandInput;
import com.github.stupremee.mela.command.util.CommandInputParser;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultDispatcher implements Dispatcher {

  private final CommandGroup root;

  @Inject
  public DefaultDispatcher(CommandGroup root) {
    this.root = root;
  }

  @Override
  public boolean dispatch(@Nonnull String command, @Nonnull CommandContext context) {
    CommandInput input = CommandInputParser.parse(root, command);
    Optional<CommandCallable> callable = input.getCallable();
    if (callable.isPresent()) {
      callable.get().call(input.getArguments(), context);
      return true;
    } else {
      return false;
    }
  }
}
