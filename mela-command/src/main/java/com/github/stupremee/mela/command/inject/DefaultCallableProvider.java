package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.github.stupremee.mela.command.compile.CommandTree;
import com.google.inject.Inject;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class DefaultCallableProvider extends SingletonProvider<CommandCallable> {

  private final CommandTree tree;
  private final CommandCompiler compiler;

  @Inject
  public DefaultCallableProvider(CommandTree tree, CommandCompiler compiler) {
    this.tree = tree;
    this.compiler = compiler;
  }

  @Override
  protected CommandCallable createInstance() {
    return compiler.compile(tree);
  }
}
