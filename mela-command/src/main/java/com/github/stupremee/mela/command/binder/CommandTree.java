package com.github.stupremee.mela.command.binder;

import java.util.Collection;

public interface CommandTree {

  Group current();

  Group stepUp();

  Group stepDown();

  Collection<Group> children();

  interface Group {

    String getName();

    ParameterBindings getParameterBindings();

    InterceptorBindings getInterceptorBindings();

    ExceptionBindings getExceptionBindings();

  }
}
