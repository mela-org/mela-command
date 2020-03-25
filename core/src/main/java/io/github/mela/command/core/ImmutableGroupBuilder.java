package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import io.github.mela.command.compile.CommandCompiler;
import io.github.mela.command.compile.IdentityCompiler;
import io.github.mela.command.compile.UncompiledGroup;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ImmutableGroupBuilder {

  private BuildingGroup current;

  ImmutableGroupBuilder() {
    current = new BuildingGroup(null, Collections.emptySet());
  }

  @Nonnull
  public ImmutableGroupBuilder group(@Nonnull String... names) {
    return group(Arrays.asList(names));
  }

  @Nonnull
  public ImmutableGroupBuilder group(@Nonnull Collection<String> names) {
    BuildingGroup child = new BuildingGroup(current, names);
    current.children.add(child);
    current = child;
    return this;
  }

  @Nonnull
  public ImmutableGroupBuilder add(@Nonnull Object command) {
    current.commands.add(checkNotNull(command));
    return this;
  }

  @Nonnull
  public ImmutableGroupBuilder parent() {
    checkState(current.parent != null,
        "Builder is at root node, no parent to step to");
    current = current.parent;
    return this;
  }

  @Nonnull
  public ImmutableGroupBuilder root() {
    while (current.parent != null) {
      parent();
    }
    return this;
  }

  @CheckReturnValue
  @Nonnull
  public CommandGroup build() {
    return compile(new IdentityCompiler());
  }

  @Nonnull
  @CheckReturnValue
  public CommandGroup compile(CommandCompiler compiler) {
    return ImmutableGroup.of(current, GroupAssembler.compiling(compiler));
  }

  private static final class BuildingGroup implements UncompiledGroup {

    private final BuildingGroup parent;
    private final Set<String> names;
    private final Set<BuildingGroup> children;
    private final Set<Object> commands;

    private BuildingGroup(BuildingGroup parent, Collection<String> names) {
      this.parent = parent;
      this.names = ImmutableSet.copyOf(names);
      children = Sets.newHashSet();
      commands = Sets.newHashSet();
    }

    @Nonnull
    @Override
    public Collection<?> getUncompiledCommands() {
      return commands;
    }

    @Nonnull
    @Override
    public Set<? extends UncompiledGroup> getChildren() {
      return children;
    }

    @Nonnull
    @Override
    public Set<String> getNames() {
      return names;
    }
  }
}
