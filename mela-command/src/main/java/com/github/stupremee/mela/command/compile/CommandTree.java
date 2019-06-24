package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.binding.ExceptionBindings;
import com.github.stupremee.mela.command.binding.InterceptorBindings;
import com.github.stupremee.mela.command.binding.ParameterBindings;
import com.github.stupremee.mela.command.inject.DefaultCommandTreeMerger;
import com.google.inject.ProvidedBy;

import java.util.Collection;
import java.util.Set;

@ProvidedBy(DefaultCommandTreeMerger.class)
public interface CommandTree {

  CommandTree EMPTY = null; // TODO: 22.06.2019

  CommandTree merge(CommandTree other);

  Group getCurrent();

  void stepUp();

  void stepToRoot();

  void stepDown(Group child);

  boolean isAtRoot();

  interface Group {

    Group getParent();

    String primaryAlias();

    Set<Group> getChildren();

    Set<String> getAliases();

    ParameterBindings getParameterBindings();

    InterceptorBindings getInterceptorBindings();

    ExceptionBindings getExceptionBindings();

    Collection<?> getCommandObjects();

  }

}
