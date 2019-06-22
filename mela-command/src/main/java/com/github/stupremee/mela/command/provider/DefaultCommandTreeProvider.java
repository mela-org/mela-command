package com.github.stupremee.mela.command.provider;

import com.github.stupremee.mela.command.CommandObjects;
import com.github.stupremee.mela.command.compile.CommandTree;
import com.github.stupremee.mela.command.compile.UnboundCommandTree;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.Collection;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultCommandTreeProvider extends SingletonProvider<CommandTree> {

  private final Set<UnboundCommandTree> unboundTrees;
  private final Collection<?> commandObjects;

  @Inject
  public DefaultCommandTreeProvider(
      Set<UnboundCommandTree> unboundTrees,
      @CommandObjects Collection<?> commandObjects) {
    this.unboundTrees = unboundTrees;
    this.commandObjects = commandObjects;
  }

  @Override
  protected CommandTree provide() {
    return unboundTrees.stream()
        .reduce(this::merge)
        .map((tree) -> tree.bind(commandObjects))
        .orElse(CommandTree.EMPTY);
  }

  private UnboundCommandTree merge(UnboundCommandTree one, UnboundCommandTree two) {
    return one.merge(two);
  }
}
