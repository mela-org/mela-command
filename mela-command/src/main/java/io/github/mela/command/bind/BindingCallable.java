package io.github.mela.command.bind;

import io.github.mela.command.bind.parameter.Parameters;
import io.github.mela.command.core.CommandCallable;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class BindingCallable implements CommandCallable {

  private final Set<String> labels;
  private final String description;
  private final String help;
  private final String usage;

  private final CommandBindings bindings;
  private final Map<Annotation, CommandInterceptor> interceptors;
  private final Parameters parameters;

  protected BindingCallable(Method method, CommandBindings bindings) {
    checkDeclaration(method);
    Command annotation = method.getAnnotation(Command.class);
    this.labels = Set.of(annotation.aliases());
    this.description = annotation.desc();
    this.help = annotation.help();
    this.usage = annotation.usage();
    this.bindings = bindings;
    this.interceptors = extractInterceptors(method, bindings);
    this.parameters = Parameters.from(method, bindings);
  }

  private void checkDeclaration(Method method) {
    String error = null;
    if (!method.isAnnotationPresent(Command.class)) {
      error = "Missing @Command annotation";
    } else if (method.getTypeParameters().length > 0) {
      error = "Method must not have type parameters";
    }

    if (error != null) {
      throw new InvalidCommandMethodException("Invalid command method declaration (" + method + "); " + error);
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

  @SuppressWarnings("unchecked")
  @Override
  public void call(@Nonnull String arguments, @Nonnull ContextMap context) {
    try {
      Arguments chain = new Arguments(arguments);
      if (!intercept(chain.subChain(), context))
        return;

      Object[] methodArguments = parameters.map(chain, context);
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
  private boolean intercept(Arguments chain, ContextMap context) {
    for (Map.Entry<Annotation, CommandInterceptor> entry : interceptors.entrySet()) {
      boolean result = entry.getValue().intercept(entry.getKey(), chain, context);
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

  @Nullable
  @Override
  public String getHelp() {
    return help;
  }

  @Override
  public String getUsage() {
    return usage;
  }

}