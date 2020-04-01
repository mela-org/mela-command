package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import io.github.mela.command.bind.parameter.Parameters;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandCallable;
import io.github.mela.command.core.CommandContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("rawtypes")
public abstract class BindingCallable implements CommandCallable {

  private final Optional<String> primaryLabel;
  private final Set<String> labels;
  private final Optional<String> description;
  private final Optional<String> help;
  private final Optional<String> usage;

  private final CommandBindings bindings;
  private final Map<Annotation, CommandInterceptor> interceptors;
  private final Parameters parameters;

  protected BindingCallable(Method method, CommandBindings bindings) {
    checkArgument(method.isAnnotationPresent(Command.class),
        "Invalid method argument (" + method + "); Missing @Command annotation");
    Command annotation = method.getAnnotation(Command.class);
    String[] labels = annotation.labels();
    this.primaryLabel = labels.length == 0
        ? Optional.empty() : Optional.of(labels[0]);
    this.labels = ImmutableSet.copyOf(labels);
    this.description = annotation.desc().isEmpty()
        ? Optional.empty() : Optional.of(annotation.desc());
    this.help = annotation.help().isEmpty()
        ? Optional.empty() : Optional.of(annotation.help());
    this.usage = annotation.usage().isEmpty()
        ? Optional.empty() : Optional.of(annotation.usage());
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
  public void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context) {
    try {
      intercept(context);
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
  private void intercept(CommandContext context) {
    for (Map.Entry<Annotation, CommandInterceptor> entry : interceptors.entrySet()) {
      entry.getValue().intercept(entry.getKey(), context);
    }
  }

  protected abstract void call(Object[] arguments) throws Throwable;

  @Nonnull
  @Override
  public Optional<String> getPrimaryLabel() {
    return primaryLabel;
  }

  @Nonnull
  @Override
  public Set<String> getLabels() {
    return labels;
  }

  @Nonnull
  @Override
  public Optional<String> getDescription() {
    return description;
  }

  @Nonnull
  @Override
  public Optional<String> getHelp() {
    return help;
  }

  @Nonnull
  @Override
  public Optional<String> getUsage() {
    return usage;
  }

  @Nonnull
  public Parameters getParameters() {
    return parameters;
  }

  @Override
  public String toString() {
    return "BindingCallable(" + labels + ")";
  }
}
