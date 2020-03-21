package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


import com.google.common.collect.Sets;
import io.github.mela.command.compile.CommandCompiler;
import io.github.mela.command.compile.IdentityCompiler;
import io.github.mela.command.compile.UncompiledGroup;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ImmutableGroupBuilder {

  private MutableGroup current;

  ImmutableGroupBuilder() {
    current = new MutableGroup(null, Collections.emptySet());
  }

  @Nonnull
  public ImmutableGroupBuilder group(@Nonnull String... names) {
    return group(Set.of(names));
  }

  @Nonnull
  public ImmutableGroupBuilder group(@Nonnull Set<String> names) {
    MutableGroup child = new MutableGroup(current, names);
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
    return compile(IdentityCompiler.INSTANCE);
  }

  @Nonnull
  @CheckReturnValue
  public CommandGroup compile(CommandCompiler compiler) {
    return ImmutableGroup.of(current, GroupAssembler.compiling(compiler));
  }

  private static final class MutableGroup implements UncompiledGroup {

    private final MutableGroup parent;
    private final Set<String> names;
    private final Set<MutableGroup> children;
    private final Set<Object> commands;

    private MutableGroup(MutableGroup parent, Set<String> names) {
      this.parent = parent;
      this.names = Set.copyOf(names);
      children = Sets.newHashSet();
      commands = Sets.newHashSet();
    }

    @Nonnull
    @Override
    public UncompiledGroup merge(@Nonnull UncompiledGroup other) {
      throw new UnsupportedOperationException();
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
