package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.collect.Maps;
import io.github.mela.command.bind.parameter.Parameters;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.CommandCallable;
import io.github.mela.command.core.CommandContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("rawtypes")
public abstract class BindingCallable implements CommandCallable {

  private final String primaryLabel;
  private final Set<String> labels;
  private final String description;
  private final String help;
  private final String usage;

  private final CommandBindings bindings;
  private final Map<Annotation, CommandInterceptor> interceptors;
  private final Parameters parameters;

  protected BindingCallable(Method method, CommandBindings bindings) {
    checkArgument(method.isAnnotationPresent(Command.class),
        "Invalid method argument (" + method + "); Missing @Command annotation");
    Command annotation = method.getAnnotation(Command.class);
    String[] labels = annotation.labels();
    this.primaryLabel = labels.length == 0 ? null : labels[0];
    this.labels = Set.of(labels);
    this.description = annotation.desc();
    this.help = annotation.help();
    this.usage = annotation.usage();
    this.bindings = checkNotNull(bindings);
    this.interceptors = extractInterceptors(method, bindings);
    this.parameters = Parameters.from(method, bindings);
  }

  private Map<Annotation, CommandInterceptor> extractInterceptors(
      Method method, CommandBindings bindings) {
    Map<Annotation, CommandInterceptor> interceptors = Maps.newLinkedHashMap();
    for (Annotation annotation : method.getAnnotations()) {
      CommandInterceptor<?> interceptor =
          bindings.getCommandInterceptor(annotation.annotationType());
      if (interceptor != null) {
        interceptors.put(annotation, interceptor);
      }
    }
    return interceptors;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void call(@Nonnull Arguments arguments, @Nonnull CommandContext context) {
    try {
      intercept(arguments, context);
      Object[] methodArguments = parameters.map(arguments, context);
      call(methodArguments);
    } catch (Throwable error) {
      ExceptionHandler handler = bindings.getHandler(error.getClass());
      if (handler == null) {
        throw new RuntimeException("Unhandled exception while calling command", error);
      } else {
        handler.handle(error, this, context);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void intercept(Arguments arguments, CommandContext context) {
    for (Map.Entry<Annotation, CommandInterceptor> entry : interceptors.entrySet()) {
      entry.getValue().intercept(entry.getKey(), arguments, context);
    }
  }

  protected abstract void call(Object[] arguments) throws Throwable;

  @Override
  public String getPrimaryLabel() {
    return primaryLabel;
  }

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
  public String getHelp() {
    return help;
  }

  @Override
  public String getUsage() {
    return usage;
  }

  @Nonnull
  public Parameters getParameters() {
    return parameters;
  }
}
