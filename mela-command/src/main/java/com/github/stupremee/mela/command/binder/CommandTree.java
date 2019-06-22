package com.github.stupremee.mela.command.binder;

import java.util.Collection;

public interface CommandTree {

  Group current();

  void stepUp();

  void stepToRoot();

  Group newChild();

  Collection<Group> children();

  interface Group {

    String getName();

    ParameterBindings getParameterBindings();

    InterceptorBindings getInterceptorBindings();

    ExceptionBindings getExceptionBindings();

  }
}
