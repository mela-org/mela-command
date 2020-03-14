package io.github.mela.command.bind;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ReflectiveCallable extends BindingCallable {

  private final MethodHandle handle;

  private ReflectiveCallable(Object delegate, Method method, CommandBindings bindings) throws NoSuchMethodException, IllegalAccessException {
    super(method, bindings);
    this.handle = constructHandle(delegate, method);
  }

  private MethodHandle constructHandle(Object delegate, Method method) throws NoSuchMethodException, IllegalAccessException {
    MethodType type = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
    return MethodHandles.publicLookup()
        .findVirtual(method.getDeclaringClass(), method.getName(), type)
        .bindTo(delegate);
  }

  @Override
  protected void call(Object[] arguments) throws Throwable {
    handle.invoke(arguments);
  }

  public static ReflectiveCallable from(Object object, Method method, CommandBindings bindings) throws NoSuchMethodException, IllegalAccessException {
    return new ReflectiveCallable(object, method, bindings);
  }

}