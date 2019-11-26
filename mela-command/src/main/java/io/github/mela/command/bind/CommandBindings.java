package io.github.mela.command.bind;

import com.google.inject.Inject;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingInterceptor;
import io.github.mela.command.bind.guice.annotation.ArgumentMappers;
import io.github.mela.command.bind.guice.annotation.CommandInterceptors;
import io.github.mela.command.bind.guice.annotation.ExceptionHandlers;
import io.github.mela.command.bind.guice.annotation.MappingInterceptors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 18.11.2019 write convenient builder
public final class CommandBindings {

  public static final CommandBindings EMPTY = new CommandBindings(Map.of(), Map.of(), Map.of(), Map.of());

  private final Map<Class,  CommandInterceptor> commandInterceptors;
  private final Map<Class, ExceptionHandler> handlers;
  private final Map<ParameterKey, ArgumentMapper> mappers;
  private final Map<Class, MappingInterceptor> argumentInterceptors;

  @Inject
  public CommandBindings(@CommandInterceptors Map<Class, CommandInterceptor> commandInterceptors,
                         @ExceptionHandlers Map<Class, ExceptionHandler> handlers,
                         @ArgumentMappers Map<ParameterKey, ArgumentMapper> mappers,
                         @MappingInterceptors Map<Class, MappingInterceptor> argumentInterceptors) {
    this.commandInterceptors = Map.copyOf(commandInterceptors);
    this.handlers = Map.copyOf(handlers);
    this.mappers = Map.copyOf(mappers);
    this.argumentInterceptors = Map.copyOf(argumentInterceptors);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public <T extends Annotation> MappingInterceptor<T> getArgumentInterceptor(@Nonnull Class<T> annotationType) {
    checkNotNull(annotationType);
    return (MappingInterceptor<T>) argumentInterceptors.get(annotationType);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public <T extends Annotation> CommandInterceptor<T> getCommandInterceptor(@Nonnull Class<T> annotationType) {
    checkNotNull(annotationType);
    return (CommandInterceptor<T>) commandInterceptors.get(annotationType);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public <T extends Throwable> ExceptionHandler<T> getHandler(@Nonnull Class<T> exceptionType) {
    checkNotNull(exceptionType);
    Class<? super T> current = exceptionType;
    do {
      ExceptionHandler<T> binding = (ExceptionHandler<T>) handlers.get(exceptionType);
      if (binding != null)
        return binding;
      current = current.getSuperclass();
    } while (current != null);
    return null;
  }

  public ArgumentMapper<?> getMapper(@Nonnull ParameterKey key) {
    return mappers.get(key);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;
    CommandBindings that = (CommandBindings) o;
    return Objects.equals(commandInterceptors, that.commandInterceptors) &&
        Objects.equals(handlers, that.handlers) &&
        Objects.equals(mappers, that.mappers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commandInterceptors, handlers, mappers);
  }
}


