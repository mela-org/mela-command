package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.CommandCallable;
import com.google.common.collect.Sets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ImmutableGroup implements CommandGroup {

  private final CommandGroup parent;
  private final Set<String> names;
  private final Set<CommandCallable> commands;

  private Set<CommandGroup> children;

  private ImmutableGroup(CommandGroup parent, Set<String> names, Set<CommandCallable> commands) {
    this.parent = parent;
    this.names = Set.copyOf(names);
    this.commands = Set.copyOf(commands);
  }

  @Nullable
  @Override
  public CommandGroup getParent() {
    return null;
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

  private void setChildren(Set<CommandGroup> children) {
    this.children = Set.copyOf(children);
  }

  public static CommandGroup copyOf(CommandGroup group) {
    ImmutableGroup copy =  new ImmutableGroup(null, group.getNames(), group.getCommands());
    copy.setChildren(deepChildrenCopy(group, copy));
    return copy;
  }

  private static Set<CommandGroup> deepChildrenCopy(CommandGroup template, CommandGroup current) {
    Set<CommandGroup> children = Sets.newHashSet();
    for (CommandGroup child : template.getChildren()) {
      ImmutableGroup next = new ImmutableGroup(current, child.getNames(), child.getCommands());
      next.setChildren(deepChildrenCopy(child, next));
      children.add(next);
    }
    return children;
  }
}
