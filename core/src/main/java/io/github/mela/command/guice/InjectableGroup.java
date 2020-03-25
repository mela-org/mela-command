package io.github.mela.command.guice;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import io.github.mela.command.compile.UncompiledGroup;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InjectableGroup implements UncompiledGroup {

  private static final Object COMMAND_PLACEHOLDER = new Object();

  private final Set<InjectableGroup> children;
  private final Set<String> names;
  private final Map<Class<?>, Object> compilables;

  InjectableGroup() {
    this(ImmutableSet.of());
  }

  private InjectableGroup(Set<String> names) {
    this.names = ImmutableSet.copyOf(names);
    this.children = Sets.newHashSet();
    this.compilables = Maps.newHashMap();
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

  InjectableGroup createChild(@Nonnull Set<String> names) {
    InjectableGroup child = new InjectableGroup(names);
    children.add(child);
    return child;
  }

}
