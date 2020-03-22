package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingInterceptor;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("rawtypes")
public final class CommandBindings {

  public static final CommandBindings EMPTY = of(
      ImmutableMap.of(), ImmutableMap.of(), ImmutableMap.of(),
      ImmutableMap.of(), ImmutableSet.of());

  private final Map<Class, CommandInterceptor> commandInterceptors;
  private final Map<Class, ExceptionHandler> handlers;
  private final Map<TypeKey, ArgumentMapper> mappers;
  private final Map<Class, MappingInterceptor> mappingInterceptors;
  private final Set<ArgumentMapperProvider> argumentMapperProviders;

  @Inject
  CommandBindings(Map<Class, CommandInterceptor> commandInterceptors,
                  Map<Class, ExceptionHandler> handlers,
                  Map<TypeKey, ArgumentMapper> mappers,
                  Map<Class, MappingInterceptor> mappingInterceptors,
                  Set<ArgumentMapperProvider> argumentMapperProviders) {
    this.commandInterceptors = commandInterceptors;
    this.handlers = handlers;
    this.mappers = mappers;
    this.mappingInterceptors = mappingInterceptors;
    this.argumentMapperProviders = argumentMapperProviders;
  }

  @CheckReturnValue
  @Nonnull
  public static CommandBindingsBuilder builder() {
    return new CommandBindingsBuilder();
  }

  @Nonnull
  public static CommandBindings of(
      @Nonnull Map<Class, CommandInterceptor> commandInterceptors,
      @Nonnull Map<Class, ExceptionHandler> handlers,
      @Nonnull Map<TypeKey, ArgumentMapper> mappers,
      @Nonnull Map<Class, MappingInterceptor> mappingInterceptors,
      @Nonnull Set<ArgumentMapperProvider> argumentMapperProviders
  ) {
    return new CommandBindings(
        ImmutableMap.copyOf(commandInterceptors), ImmutableMap.copyOf(handlers),
        ImmutableMap.copyOf(mappers), ImmutableMap.copyOf(mappingInterceptors),
        ImmutableSet.copyOf(argumentMapperProviders));
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public <T extends Annotation> MappingInterceptor<T> getMappingInterceptor(
      @Nonnull Class<T> annotationType) {
    checkNotNull(annotationType);
    return (MappingInterceptor<T>) mappingInterceptors.get(annotationType);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public <T extends Annotation> CommandInterceptor<T> getCommandInterceptor(
      @Nonnull Class<T> annotationType) {
    checkNotNull(annotationType);
    return (CommandInterceptor<T>) commandInterceptors.get(annotationType);
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public <T extends Throwable> ExceptionHandler<T> getHandler(@Nonnull Class<T> exceptionType) {
    checkNotNull(exceptionType);
    Class<? super T> current = exceptionType;
    do {
      ExceptionHandler<T> binding = (ExceptionHandler<T>) handlers.get(current);
      if (binding != null) {
        return binding;
      }
      current = current.getSuperclass();
    } while (current != null);
    return null;
  }

  @Nullable
  public ArgumentMapper<?> getMapper(@Nonnull TargetType type) {
    ArgumentMapper<?> mapper = mappers.get(type.getTypeKey());
    return mapper != null
        ? mapper
        : argumentMapperProviders
        .stream()
        .filter((provider) -> provider.canProvideFor(type))
        .findFirst()
        .map((provider) -> provider.provideFor(type, this))
        .orElse(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandBindings that = (CommandBindings) o;
    return Objects.equals(commandInterceptors, that.commandInterceptors)
        && Objects.equals(handlers, that.handlers)
        && Objects.equals(mappers, that.mappers)
        && Objects.equals(mappingInterceptors, that.mappingInterceptors)
        && Objects.equals(argumentMapperProviders, that.argumentMapperProviders);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commandInterceptors, handlers, mappers,
        mappingInterceptors, argumentMapperProviders);
  }

  @Override
  public String toString() {
    return "CommandBindings{"
        + "commandInterceptors=" + commandInterceptors
        + ", handlers=" + handlers
        + ", mappers=" + mappers
        + ", mappingInterceptors=" + mappingInterceptors
        + ", argumentMapperProviders=" + argumentMapperProviders
        + '}';
  }
}


