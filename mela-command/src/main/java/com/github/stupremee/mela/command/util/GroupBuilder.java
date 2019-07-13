package com.github.stupremee.mela.command.util;

import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.DelegatedGroupBindings;
import com.github.stupremee.mela.command.GroupAccumulator;
import com.github.stupremee.mela.command.GroupBindings;
import com.github.stupremee.mela.command.ImmutableGroup;
import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.github.stupremee.mela.command.compile.UncompiledGroup;
import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Key;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class GroupBuilder {

  private MutableGroup current;

  private GroupBuilder() {
    current = new MutableGroup(null, Collections.emptySet());
  }

  public static GroupBuilder create() {
    return new GroupBuilder();
  }

  public GroupBuilder group(String... names) {
    return group(Set.of(names));
  }

  public GroupBuilder group(Set<String> names) {
    MutableGroup child = new MutableGroup(current, Set.copyOf(names));
    current.children.add(child);
    current = child;
    return this;
  }

  public GroupBuilder command(Object command) {
    current.commands.add(command);
    return this;
  }

  public <T extends Annotation> GroupBuilder intercept(Class<T> annotationType, Interceptor<T> interceptor) {
    current.bindings.addInterceptor(annotationType, interceptor);
    return this;
  }

  public <T extends Throwable> GroupBuilder handle(Class<T> exceptionType, ExceptionHandler<T> handler) {
    current.bindings.addHandler(exceptionType, handler);
    return this;
  }

  public <T> GroupBuilder map(Key<T> key, ArgumentMapper<T> mapper) {
    current.bindings.addMapper(key, mapper);
    return this;
  }

  public GroupBuilder parent() {
    checkState(current.parent != null, "Builder is at root node, no parent to step to");
    current = current.parent;
    return this;
  }

  public GroupBuilder root() {
    while (current.parent != null) {
      parent();
    }
    return this;
  }

  public CommandGroup compile(CommandCompiler compiler) {
    return ImmutableGroup.of(current, GroupAccumulator.compiling(compiler));
  }

  private static final class MutableGroup implements UncompiledGroup {

    private final MutableGroup parent;
    private final Set<String> names;
    private final Set<MutableGroup> children;
    private final Set<Object> commands;
    private final MutableBindings bindings;

    private MutableGroup(MutableGroup parent, Set<String> names) {
      this.parent = parent;
      this.names = names;
      children = new HashSet<>();
      commands = new HashSet<>();
      this.bindings = parent == null
          ? new MutableBindings(null)
          : parent.bindings;
    }

    @Nonnull
    @Override
    public UncompiledGroup merge(@Nonnull UncompiledGroup other) {
      throw new UnsupportedOperationException();
    }

    @Override
    public Collection<?> getUncompiledCommands() {
      return commands;
    }

    @Override
    public GroupBindings getBindings() {
      return bindings;
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

  private static final class MutableBindings extends DelegatedGroupBindings {

    MutableBindings(MutableBindings parent) {
      super(parent);
    }

    <T extends Annotation> void addInterceptor(Class<T> annotationType, Interceptor<T> interceptor) {
      interceptors.put(annotationType, () -> interceptor);
    }

    <T extends Throwable> void addHandler(Class<T> exceptionType, ExceptionHandler<T> handler) {
      handlers.put(exceptionType, () -> handler);
    }

    <T> void addMapper(Key<T> key, ArgumentMapper<T> mapper) {
      mappers.put(key, () -> mapper);
    }
  }
}
