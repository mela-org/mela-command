package io.github.mela.command.bind.guice;

import com.google.inject.Inject;
import io.github.mela.command.compile.CommandCompiler;
import io.github.mela.command.compile.UncompiledGroup;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.GroupAssembler;
import io.github.mela.command.core.ImmutableGroup;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CompilingRootGroupProvider extends SingletonProvider<CommandGroup> {

  private final Set<UncompiledGroup> rawGroups;
  private final CommandCompiler compiler;

  @Inject
  public CompilingRootGroupProvider(Set<UncompiledGroup> rawGroups, CommandCompiler compiler) {
    this.rawGroups = rawGroups;
    this.compiler = compiler;
  }

  @Nonnull
  @Override
  public CommandGroup create() {
    return rawGroups.stream()
        .reduce(UncompiledGroup::merge)
        .map((group) -> ImmutableGroup.of(group, GroupAssembler.compiling(compiler)))
        .orElse(ImmutableGroup.EMPTY);
  }
}
