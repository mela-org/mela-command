package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.binder.CommandTree;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CallableProvider implements Provider<CommandCallable> {

  private final CommandTree tree;
  private final CommandCompiler compiler;

  private CommandCallable compiledCallable;

  @Inject
  public CallableProvider(CommandTree tree, CommandCompiler compiler) {
    this.tree = tree;
    this.compiler = compiler;
  }

  @Override
  public CommandCallable get() {
    if (compiledCallable != null)
      return compiledCallable;

    compiledCallable = compiler.compile(tree);
    return compiledCallable;
  }
}
