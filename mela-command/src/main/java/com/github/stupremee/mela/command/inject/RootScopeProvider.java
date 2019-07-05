package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.github.stupremee.mela.command.bind.tree.CommandTree;
import com.google.inject.Inject;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class RootScopeProvider extends LazySingletonProvider<CommandGroup> {

  private final CommandTree tree;
  private final CommandCompiler compiler;

  @Inject
  public RootScopeProvider(CommandTree tree, CommandCompiler compiler) {
    this.tree = tree;
    this.compiler = compiler;
  }

  @Override
  protected CommandGroup createInstance() {
    return compiler.compile(tree);
  }
}
