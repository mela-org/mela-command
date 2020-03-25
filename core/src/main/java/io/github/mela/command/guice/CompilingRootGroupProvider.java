package io.github.mela.command.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import io.github.mela.command.compile.CommandCompiler;
import io.github.mela.command.compile.UncompiledGroup;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.GroupAssembler;
import io.github.mela.command.core.ImmutableGroup;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CompilingRootGroupProvider implements Provider<CommandGroup> {

  private final Set<UncompiledGroup> rawGroups;
  private final CommandCompiler compiler;

  @Inject
  public CompilingRootGroupProvider(Set<UncompiledGroup> rawGroups, CommandCompiler compiler) {
    this.rawGroups = rawGroups;
    this.compiler = compiler;
  }

  @Nonnull
  @Override
  public CommandGroup get() {
    return ImmutableGroup.merge(rawGroups.stream()
        .map((group) -> ImmutableGroup.of(group, GroupAssembler.compiling(compiler)))
        .collect(Collectors.toList()));
  }
}
