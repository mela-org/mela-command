package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.binding.ExceptionBindings;
import com.github.stupremee.mela.command.binding.InterceptorBindings;
import com.github.stupremee.mela.command.binding.ParameterBindings;
import com.github.stupremee.mela.command.inject.DefaultCommandTreeProvider;
import com.google.inject.ProvidedBy;

import java.util.Collection;
import java.util.List;

@ProvidedBy(DefaultCommandTreeProvider.class)
public interface CommandTree {

  CommandTree EMPTY = null; // TODO: 22.06.2019

  Group getCurrent();

  void stepUp();

  void stepToRoot();

  void stepDown(int childIndex);

  void stepDown(Group child);

  boolean isAtRoot();

  interface Group {

    Group getParent();

    List<Group> getChildren();

    List<String> getAliases();

    ParameterBindings getParameterBindings();

    InterceptorBindings getInterceptorBindings();

    ExceptionBindings getExceptionBindings();

    Collection<?> getCommandObjects();

  }

}
