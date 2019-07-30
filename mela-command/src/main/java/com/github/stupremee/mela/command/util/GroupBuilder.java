package com.github.stupremee.mela.command.util;

import com.github.stupremee.mela.command.*;
import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.github.stupremee.mela.command.compile.IdentityCompiler;
import com.github.stupremee.mela.command.compile.UncompiledGroup;
import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Key;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class GroupBuilder {

  private MutableGroup current;

  private GroupBuilder() {
    current = new MutableGroup(null, Collections.emptySet());
  }

  @Nonnull
  public static GroupBuilder create() {
    return new GroupBuilder();
  }

  @Nonnull
  public GroupBuilder group(@Nonnull String... names) {
    return group(Set.of(names));
  }

  @Nonnull
  public GroupBuilder group(Set<String> names) {
    MutableGroup child = new MutableGroup(current, names);
    current.children.add(child);
    current = child;
    return this;
  }

  @Nonnull
  public GroupBuilder withCommand(Object command) {
    current.commands.add(command);
    return this;
  }

  @Nonnull
  public <T extends Annotation> GroupBuilder withInterceptor(@Nonnull Class<T> annotationType,
                                                             @Nullable Interceptor<T> interceptor) {
    current.bindings.addInterceptor(annotationType, interceptor);
    return this;
  }

  @Nonnull
  public <T extends Throwable> GroupBuilder withHandler(@Nonnull Class<T> exceptionType,
                                                        @Nullable ExceptionHandler<T> handler) {
    current.bindings.addHandler(exceptionType, handler);
    return this;
  }

  @Nonnull
  public <T> GroupBuilder withMapper(@Nonnull Key<T> key,
                                     @Nullable ArgumentMapper<T> mapper) {
    current.bindings.addMapper(key, mapper);
    return this;
  }

  @Nonnull
  public GroupBuilder parent() {
    checkState(current.parent != null, "Builder is at root node, no parent to step to");
    current = current.parent;
    return this;
  }

  @Nonnull
  public GroupBuilder root() {
    while (current.parent != null) {
      parent();
    }
    return this;
  }

  @Nonnull
  @CheckReturnValue
  public CommandGroup compileIdentity() {
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
    private final MutableBindings bindings;

    private MutableGroup(MutableGroup parent, Set<String> names) {
      this.parent = parent;
      this.names = Set.copyOf(names);
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

    @Nonnull
    @Override
    public Collection<?> getUncompiledCommands() {
      return commands;
    }

    @Nonnull
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
      interceptors.put(checkNotNull(annotationType), () -> interceptor);
    }

    <T extends Throwable> void addHandler(Class<T> exceptionType, ExceptionHandler<T> handler) {
      handlers.put(checkNotNull(exceptionType), () -> handler);
    }

    <T> void addMapper(Key<T> key, ArgumentMapper<T> mapper) {
      mappers.put(checkNotNull(key), () -> mapper);
    }
  }
}
