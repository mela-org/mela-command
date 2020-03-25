package io.github.mela.command.guice;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


import com.google.common.collect.ImmutableSet;
import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import io.github.mela.command.compile.UncompiledGroup;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandBindingNode {

  private final CommandBindingNode parent;
  private final Multibinder<Object> commandObjectBinder;
  private final InjectableGroup group;

  CommandBindingNode(Binder binder) {
    this.parent = null;
    this.group = new InjectableGroup();
    this.commandObjectBinder = Multibinder.newSetBinder(binder, Object.class, Commands.class);
    Multibinder<UncompiledGroup> groupBinder =
        Multibinder.newSetBinder(binder, UncompiledGroup.class);
    groupBinder.addBinding().toInstance(group);
  }

  private CommandBindingNode(CommandBindingNode parent, InjectableGroup root) {
    this.parent = parent;
    this.commandObjectBinder = parent.commandObjectBinder;
    this.group = root;
  }

  @Nonnull
  public CommandBindingNode group(@Nonnull String... aliases) {
    checkNotNull(aliases);
    InjectableGroup child = group.createChild(ImmutableSet.copyOf(aliases));
    return new CommandBindingNode(this, child);
  }

  @Nonnull
  public CommandBindingNode parent() {
    checkState(parent != null,
        "Cannot go to parent, this is the highest node");
    return parent;
  }

  // TODO extend to full guice compatibility
  @Nonnull
  public CommandBindingNode add(@Nonnull Class<?> commandClass) {
    checkNotNull(commandClass);
    group.addCommand(commandClass);
    commandObjectBinder.addBinding().to(commandClass);
    return this;
  }

  @Nonnull
  public CommandBindingNode add(@Nonnull Object command) {
    checkNotNull(command);
    group.addCommand(command.getClass());
    commandObjectBinder.addBinding().toInstance(command);
    return this;
  }

}
