package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.compile.CompilableGroup;
import com.github.stupremee.mela.command.EmptyGroup;
import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class RootGroupProvider extends LazySingletonProvider<CommandGroup> {

  private final Set<CompilableGroup> rawGroups;
  private final CommandCompiler compiler;

  @Inject
  public RootGroupProvider(Set<CompilableGroup> rawGroups, CommandCompiler compiler) {
    this.rawGroups = rawGroups;
    this.compiler = compiler;
  }

  @Nonnull
  @Override
  protected CommandGroup createInstance() {
    return rawGroups.stream()
        .reduce(CompilableGroup::merge)
        .map(compiler::compile)
        .orElse(EmptyGroup.INSTANCE);
  }
}
