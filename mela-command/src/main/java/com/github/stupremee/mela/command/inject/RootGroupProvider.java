package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.CompilableGroup;
import com.github.stupremee.mela.command.EmptyGroup;
import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.ImmutableGroup;
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
        .reduce((one, two) -> {
          one.assimilate(two);
          return one;
        }).map((group) -> {
          group.compile(compiler);
          return ImmutableGroup.copyOf(group);
        }).orElse(EmptyGroup.INSTANCE);
  }
}
