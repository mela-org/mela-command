package com.github.stupremee.mela.command.guice;

import com.github.stupremee.mela.command.core.GroupAssembler;
import com.github.stupremee.mela.command.core.ImmutableGroup;
import com.github.stupremee.mela.command.core.EmptyGroup;
import com.github.stupremee.mela.command.core.CommandGroup;
import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.github.stupremee.mela.command.compile.UncompiledGroup;
import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CompilingRootGroupProvider extends LazySingletonProvider<CommandGroup> {

  private final Set<UncompiledGroup> rawGroups;
  private final CommandCompiler compiler;

  @Inject
  public CompilingRootGroupProvider(Set<UncompiledGroup> rawGroups, CommandCompiler compiler) {
    this.rawGroups = rawGroups;
    this.compiler = compiler;
  }

  @Nonnull
  @Override
  protected CommandGroup createInstance() {
    return rawGroups.stream()
        .reduce(UncompiledGroup::merge)
        .map((group) -> ImmutableGroup.of(group, GroupAssembler.compiling(compiler)))
        .orElse(EmptyGroup.INSTANCE);
  }
}
