package io.github.mela.command.bind.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.mela.command.compile.CommandCompiler;
import io.github.mela.command.compile.UncompiledGroup;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.GroupAssembler;
import io.github.mela.command.core.ImmutableGroup;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CompilingRootGroupProvider implements Provider<CommandGroup> {

  private final Set<UncompiledGroup> rawGroups;
  private final CommandCompiler compiler;

  private CommandGroup root;

  @Inject
  public CompilingRootGroupProvider(Set<UncompiledGroup> rawGroups, CommandCompiler compiler) {
    this.rawGroups = rawGroups;
    this.compiler = compiler;
    this.root = null;
  }

  @Nonnull
  @Override
  public CommandGroup get() {
    if (root != null) {
      return root;
    }

    root = rawGroups.stream()
        .reduce(UncompiledGroup::merge)
        .map((group) -> ImmutableGroup.of(group, GroupAssembler.compiling(compiler)))
        .orElse(ImmutableGroup.EMPTY);
    return root;
  }
}
