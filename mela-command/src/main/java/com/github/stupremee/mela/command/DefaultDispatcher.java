package com.github.stupremee.mela.command;

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
    GroupFinder finder = GroupFinder.of(root, command);
    finder.find();
    CommandGroup group = finder.getResult();
    String remaining = finder.getRemainingInput();
    int spaceIndex = remaining.indexOf(' ');
    String commandLabel = remaining.substring(0, spaceIndex);
    String arguments = remaining.substring(spaceIndex).trim();
    Optional<CommandCallable> commandCallable = group.getCommands().stream()
        .filter((callable) -> callable.getLabels().contains(commandLabel))
        .findFirst();
    if (commandCallable.isPresent()) {
      commandCallable.get().call(arguments, context);
      return true;
    } else {
      return false;
    }
  }
}
