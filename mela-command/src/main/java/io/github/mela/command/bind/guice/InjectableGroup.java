package io.github.mela.command.bind.guice;

import com.google.inject.Inject;
import io.github.mela.command.compile.UncompiledGroup;

import javax.annotation.Nonnull;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InjectableGroup implements UncompiledGroup {

  private static final Object COMMAND_PLACEHOLDER = new Object();

  private final Set<InjectableGroup> children;
  private final Set<String> names;
  private final Map<Class<?>, Object> compilables;

  InjectableGroup() {
    this(Set.of());
  }

  private InjectableGroup(Set<String> names) {
    this.names = Set.copyOf(names);
    this.children = new HashSet<>();
    this.compilables = new HashMap<>();
  }

  @Nonnull
  @Override
  public UncompiledGroup merge(@Nonnull UncompiledGroup other) {
    checkArgument(checkNotNull(other) instanceof InjectableGroup,
        "Group to assimilate must be of the same type as this group");
    InjectableGroup root = (InjectableGroup) other;
    InjectableGroup copy = this.copy();
    assimilate(copy, root);
    return copy;
  }

  private InjectableGroup copy() {
    InjectableGroup copy = new InjectableGroup();
    this.assimilate(copy, this);
    return copy;
  }

  private void assimilate(InjectableGroup own, InjectableGroup other) {
    own.compilables.putAll(other.compilables);
    for (InjectableGroup otherChild : other.children) {
      InjectableGroup ownChild = own.createChildIfNotExists(otherChild.names);
      assimilate(ownChild, otherChild);
    }
  }

  @Nonnull
  @Override
  public Collection<?> getUncompiledCommands() {
    return Collections.unmodifiableCollection(compilables.values());
  }

  @Nonnull
  @Override
  public Set<UncompiledGroup> getChildren() {
    return Collections.unmodifiableSet(children);
  }

  @Nonnull
  @Override
  public Set<String> getNames() {
    return names;
  }

  @Inject
  void inject(@Commands Set<Object> commands) {
    recursiveInject(this, commands);
  }

  private void recursiveInject(InjectableGroup current, Set<Object> commands) {
    for (Object command : commands) {
      current.compilables.computeIfPresent(command.getClass(), (k, v) -> command);
    }
    for (InjectableGroup child : current.children) {
      recursiveInject(child, commands);
    }
  }

  void addCommand(Class<?> commandClass) {
    compilables.put(commandClass, COMMAND_PLACEHOLDER);
  }

  InjectableGroup createChildIfNotExists(@Nonnull Set<String> names) {
    for (InjectableGroup child : children) {
      if (child.names.equals(names)) {
        return child;
      }
    }

    InjectableGroup child = new InjectableGroup(names);
    children.add(child);
    return child;
  }

}
