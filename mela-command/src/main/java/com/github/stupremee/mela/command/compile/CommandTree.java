package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.binding.ExceptionBindings;
import com.github.stupremee.mela.command.binding.InterceptorBindings;
import com.github.stupremee.mela.command.binding.ParameterBindings;
import com.github.stupremee.mela.command.inject.MergingCommandTreeProvider;
import com.github.stupremee.mela.command.internal.empty.EmptyCommandTree;
import com.google.inject.ProvidedBy;

import javax.annotation.Nonnull;
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

    Group getParent();

    String primaryAlias();

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
