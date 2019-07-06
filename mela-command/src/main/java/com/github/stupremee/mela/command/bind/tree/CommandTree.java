package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.bind.ExceptionBindings;
import com.github.stupremee.mela.command.bind.InterceptorBindings;
import com.github.stupremee.mela.command.bind.ParameterBindings;
import com.github.stupremee.mela.command.inject.MergingCommandTreeProvider;
import com.google.inject.ProvidedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

@ProvidedBy(MergingCommandTreeProvider.class)
public interface CommandTree {

  CommandTree EMPTY = EmptyCommandTree.INSTANCE;

  @Nonnull
  CommandTree merge(@Nonnull CommandTree other);

  @Nonnull
  Group getCurrent();

  void stepUp();

  default void stepToRoot() {
    while (!isAtRoot())
      stepUp();
  }

  void stepDown(@Nonnull Group child);

  boolean isAtRoot();

  interface Group {

    @Nullable
    Group getParent();

    @Nullable
    String getPrimaryAlias();

    @Nonnull
    Set<Group> getChildren();

    @Nonnull
    Set<String> getAliases();

    @Nonnull
    ParameterBindings getParameterBindings();

    @Nonnull
    InterceptorBindings getInterceptorBindings();

    @Nonnull
    ExceptionBindings getExceptionBindings();

    @Nonnull
    Collection<?> getCommandObjects();

  }

}
