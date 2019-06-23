package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.compile.CommandTree;
import com.github.stupremee.mela.command.compile.InjectableCommandTree;
import com.github.stupremee.mela.command.inject.InjectionObjectHolder;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class InternalInjectableCommandTree implements InjectableCommandTree {



  @Override
  public InjectableCommandTree merge(InjectableCommandTree other) {
    return null;
  }

  @Override
  public CommandTree inject(InjectionObjectHolder holder) {
    return null;
  }

  private static final class InjectableGroup {
    final InternalParameterBindings parameterBindings;
    final InternalInterceptorBindings interceptorBindings;
    final InternalExceptionBindings exceptionBindings;

    final Map<Class<?>, ?> commands;

    InjectableGroup() {
      this(new InternalParameterBindings(),
          new InternalInterceptorBindings(), new InternalExceptionBindings());
    }

    InjectableGroup(InternalParameterBindings parameterBindings,
                            InternalInterceptorBindings interceptorBindings,
                            InternalExceptionBindings exceptionBindings) {
      this.parameterBindings = parameterBindings;
      this.interceptorBindings = interceptorBindings;
      this.exceptionBindings = exceptionBindings;
      this.commands = Maps.newHashMap();
    }

    InjectableGroup copy() {
      return new InjectableGroup(parameterBindings.copy(),
          interceptorBindings.copy(), exceptionBindings.copy());
    }
  }
}
