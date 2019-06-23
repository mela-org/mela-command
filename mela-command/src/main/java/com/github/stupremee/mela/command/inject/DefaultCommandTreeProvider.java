package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.compile.CommandTree;
import com.github.stupremee.mela.command.compile.InjectableCommandTree;
import com.google.inject.Inject;

import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultCommandTreeProvider extends SingletonProvider<CommandTree> {

  private final Set<InjectableCommandTree> unboundTrees;
  private final InjectionObjectHolder holder;

  @Inject
  public DefaultCommandTreeProvider(
      Set<InjectableCommandTree> unboundTrees,
      InjectionObjectHolder holder) {
    this.unboundTrees = unboundTrees;
    this.holder = holder;
  }

  @Override
  protected CommandTree createInstance() {
    return unboundTrees.stream()
        .reduce(InjectableCommandTree::merge)
        .map((tree) -> tree.inject(holder))
        .orElse(CommandTree.EMPTY);
  }
}
