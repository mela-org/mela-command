package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.binding.ExceptionBindings;
import com.github.stupremee.mela.command.binding.InterceptorBindings;
import com.github.stupremee.mela.command.binding.ParameterBindings;
import com.github.stupremee.mela.command.inject.DefaultCommandTreeMerger;
import com.google.inject.ProvidedBy;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

@ProvidedBy(DefaultCommandTreeMerger.class)
public interface CommandTree {

  CommandTree EMPTY = null; // TODO: 22.06.2019

  @Nonnull
  CommandTree merge(@Nonnull CommandTree other);

  @Nonnull
  Group getCurrent();

  void stepUp();

  void stepToRoot();

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
