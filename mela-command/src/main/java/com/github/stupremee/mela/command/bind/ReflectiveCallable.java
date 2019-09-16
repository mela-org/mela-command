package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.bind.BindingCallable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ReflectiveCallable extends BindingCallable {

  private final Method method;
  private final Object delegate;

  private ReflectiveCallable(Object delegate, Method method, CommandBindings bindings) {
    super(method, bindings);
    this.method = method;
    this.delegate = delegate;
  }

  @Override
  protected void call(Object[] arguments) throws InvocationTargetException, IllegalAccessException {
    method.invoke(delegate, arguments);
  }

}
