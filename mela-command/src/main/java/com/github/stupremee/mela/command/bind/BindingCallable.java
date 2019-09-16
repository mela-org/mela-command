package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.parse.Arguments;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class BindingCallable implements CommandCallable {

  private final CommandBindings bindings;
  private final Map<Annotation, CommandInterceptor> interceptors;
  private final Parameters parameters;

  protected BindingCallable(Method method, CommandBindings bindings) {
    this.bindings = bindings;
    this.interceptors = extractInterceptors(method, bindings);
    this.parameters = Parameters.from(method, bindings);
  }

  private Map<Annotation, CommandInterceptor> extractInterceptors(Method method, CommandBindings bindings) {
    Map<Annotation, CommandInterceptor> interceptors = new LinkedHashMap<>();
    for (Annotation annotation : method.getAnnotations()) {
      CommandInterceptor<?> interceptor = bindings.getCommandInterceptor(annotation.annotationType());
      if (interceptor != null) {
        interceptors.put(annotation, interceptor);
      }
    }
    return Map.copyOf(interceptors);
  }

  protected BindingCallable(CommandBindings bindings, Map<Annotation, CommandInterceptor> interceptors, Parameters parameters) {
    this.bindings = bindings;
    this.interceptors = interceptors;
    this.parameters = parameters;
  }

  @Override
  public void call(@Nonnull Arguments arguments, @Nonnull CommandContext context) {
    try {
      if (!intercept(context))
        return;

      Object[] methodArguments = parameters.map(arguments, context);
      call(methodArguments);
    } catch (Throwable error) {
      ExceptionHandler handler = bindings.getHandler(error.getClass());
      if (handler == null) {
        throw new RuntimeException("Unhandled exception while calling command", error);
      } else {
        handler.handle(error, context);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private boolean intercept(CommandContext context) throws Throwable {
    for (Map.Entry<Annotation, CommandInterceptor> entry : interceptors.entrySet()) {
      boolean result = entry.getValue().intercept(entry.getKey(), context);
      if (!result)
        return false;
    }
    return true;
  }

  protected abstract void call(Object[] arguments);

}
