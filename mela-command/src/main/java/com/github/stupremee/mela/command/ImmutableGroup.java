package com.github.stupremee.mela.command;

import com.google.common.collect.Sets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ImmutableGroup implements CommandGroup {

  private final ImmutableGroup parent;
  private final Set<String> names;
  private final Set<CommandCallable> commands;

  private Set<CommandGroup> children;

  private ImmutableGroup(ImmutableGroup parent, Set<String> names, Set<CommandCallable> commands) {
    this.parent = parent;
    this.names = Set.copyOf(names);
    this.commands = Set.copyOf(commands);
  }

  @Nullable
  @Override
  public CommandGroup getParent() {
    return parent;
  }

  @Nonnull
  @Override
  public Set<CommandGroup> getChildren() {
    return children;
  }

  @Nonnull
  @Override
  public Set<String> getNames() {
    return names;
  }

  @Nonnull
  @Override
  public Set<CommandCallable> getCommands() {
    return commands;
  }

  @Override
  public String toString() {
    StringBuilder pathBuilder = new StringBuilder();
    ImmutableGroup group = this;
    while (group != null) {
      pathBuilder.insert(0, group.names);
      if (group.parent != null)
        pathBuilder.insert(0, " - ");
      group = group.parent;
    }
    return pathBuilder.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ImmutableGroup that = (ImmutableGroup) o;
    return Objects.equals(names, that.names) &&
        Objects.equals(commands, that.commands) &&
        Objects.equals(children, that.children);
  }

  @Override
  public int hashCode() {
    return Objects.hash(names, commands, children);
  }

  private void setChildren(Set<ImmutableGroup> children) {
    checkState(this.children == null);
    this.children = Set.copyOf(children);
  }

  public static CommandGroup copyOf(CommandGroup group) {
    ImmutableGroup copy =  new ImmutableGroup(null, group.getNames(), group.getCommands());
    copy.setChildren(deepChildrenCopy(group, copy));
    return copy;
  }

  private static Set<ImmutableGroup> deepChildrenCopy(CommandGroup template, ImmutableGroup current) {
    Set<ImmutableGroup> children = Sets.newHashSet();
    for (CommandGroup child : template.getChildren()) {
      ImmutableGroup next = new ImmutableGroup(current, child.getNames(), child.getCommands());
      next.setChildren(deepChildrenCopy(child, next));
      children.add(next);
    }
    return children;
  }
}
