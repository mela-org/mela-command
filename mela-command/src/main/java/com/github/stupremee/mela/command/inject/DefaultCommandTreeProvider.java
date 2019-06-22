package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.compile.CommandTree;
import com.github.stupremee.mela.command.compile.UninjectedCommandTree;
import com.google.inject.Inject;

import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultCommandTreeProvider extends SingletonProvider<CommandTree> {

  private final Set<UninjectedCommandTree> unboundTrees;
  private final InjectionObjectHolder holder;

  @Inject
  public DefaultCommandTreeProvider(
      Set<UninjectedCommandTree> unboundTrees,
      InjectionObjectHolder holder) {
    this.unboundTrees = unboundTrees;
    this.holder = holder;
  }

  @Override
  protected CommandTree createInstance() {
    return unboundTrees.stream()
        .reduce(UninjectedCommandTree::merge)
        .map((tree) -> tree.inject(holder))
        .orElse(CommandTree.EMPTY);
  }
}
