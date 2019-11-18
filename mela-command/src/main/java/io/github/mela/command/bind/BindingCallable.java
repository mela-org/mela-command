package io.github.mela.command.bind;

import io.github.mela.command.bind.parameter.Parameters;
import io.github.mela.command.core.CommandCallable;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.parse.Arguments;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class BindingCallable implements CommandCallable {

  private final Set<String> labels;
  private final String description;
  private final String usage;

  private final CommandBindings bindings;
  private final Map<Annotation, CommandInterceptor> interceptors;
  private final Parameters parameters;

  protected BindingCallable(Method method, CommandBindings bindings) {
    checkDeclaration(method);
    Command annotation = method.getAnnotation(Command.class);
    this.labels = Set.of(annotation.aliases());
    this.description = annotation.desc();
    this.usage = annotation.usage();
    this.bindings = bindings;
    this.interceptors = extractInterceptors(method, bindings);
    this.parameters = Parameters.from(method, bindings);
  }

  private void checkDeclaration(Method method) {
    boolean valid = method.isAnnotationPresent(Command.class)
        && method.getReturnType().equals(void.class)
        && Modifier.isPublic(method.getModifiers())
        && !Modifier.isStatic(method.getModifiers())
        && method.getTypeParameters().length == 0;
    if (!valid) {
      throw new RuntimeException("Invalid command method declaration (" + method + "). " +
          "Command methods must be annotated with @Command, they must be public, non-static, " +
          "return void and not have any type parameters.");
    }
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

  protected abstract void call(Object[] arguments) throws Throwable;

  @Nonnull
  @Override
  public Set<String> getLabels() {
    return labels;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getUsage() {
    return usage;
  }

}
