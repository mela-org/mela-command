package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.binder.CommandTree;
import com.github.stupremee.mela.command.binder.UnboundCommandTree;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.Collection;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandTreeProvider implements Provider<CommandTree> {

  private final Set<UnboundCommandTree> unboundTrees;
  private final Collection<?> commandObjects;
  private CommandTree tree;


  @Inject
  public CommandTreeProvider(Set<UnboundCommandTree> unboundTrees, @CommandObjects Collection<?> commandObjects) {
    this.unboundTrees = unboundTrees;
    this.commandObjects = commandObjects;
  }


  @Override
  public CommandTree get() {
    if (tree != null)
      return tree;

    tree = unboundTrees.stream()
        .reduce(this::merge)
        .map((tree) -> tree.bind(commandObjects))
        .orElse(CommandTree.EMPTY);
    return tree;
  }

  private UnboundCommandTree merge(UnboundCommandTree one, UnboundCommandTree two) {
    return one.merge(two);
  }
}
