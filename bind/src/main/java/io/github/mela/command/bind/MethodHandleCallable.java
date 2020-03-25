package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkNotNull;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MethodHandleCallable extends BindingCallable {

  private final MethodHandle handle;

  protected MethodHandleCallable(Object delegate, Method method, CommandBindings bindings)
      throws NoSuchMethodException, IllegalAccessException {
    super(method, bindings);
    checkNotNull(delegate);
    this.handle = constructHandle(delegate, method);
  }

  @Nonnull
  public static MethodHandleCallable from(
      @Nonnull Object object,
      @Nonnull Method method,
      @Nonnull CommandBindings bindings
  ) throws NoSuchMethodException, IllegalAccessException {
    return new MethodHandleCallable(object, method, bindings);
  }

  private MethodHandle constructHandle(Object delegate, Method method)
      throws NoSuchMethodException, IllegalAccessException {
    MethodType type = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
    return MethodHandles.publicLookup()
        .findVirtual(method.getDeclaringClass(), method.getName(), type)
        .bindTo(delegate);
  }

  @Override
  protected void call(Object[] arguments) throws Throwable {
    handle.invokeWithArguments(arguments);
  }

}
