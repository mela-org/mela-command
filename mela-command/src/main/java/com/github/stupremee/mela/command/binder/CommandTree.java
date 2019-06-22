package com.github.stupremee.mela.command.binder;

import com.github.stupremee.mela.command.CommandTreeProvider;
import com.google.inject.ProvidedBy;

import java.util.Collection;
import java.util.List;

@ProvidedBy(CommandTreeProvider.class)
public interface CommandTree {

  CommandTree EMPTY = null; // TODO: 22.06.2019

  Group current();

  void stepUp();

  void stepToRoot();

  void stepDown(int childIndex);

  List<Group> children();

  interface Group {

    List<Group> getChildren();

    List<String> getAliases();

    ParameterBindings getParameterBindings();

    InterceptorBindings getInterceptorBindings();

    ExceptionBindings getExceptionBindings();

    Collection<?> getCommandObjects();

  }

}
