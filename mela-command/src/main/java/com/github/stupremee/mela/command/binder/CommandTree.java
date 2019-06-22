package com.github.stupremee.mela.command.binder;

import java.util.Collection;
import java.util.List;

public interface CommandTree {

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
