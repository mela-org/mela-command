package io.github.mela.command.bind;

import com.google.common.reflect.TypeToken;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingInterceptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandBindingsBuilder {

  private final Map<Class, CommandInterceptor> commandInterceptors;
  private final Map<Class, ExceptionHandler> handlers;
  private final Map<TypeKey, ArgumentMapper> mappers;
  private final Map<Class, MappingInterceptor> mappingInterceptors;
  private final Set<ArgumentMapperProvider> argumentMapperProviders;

  private CommandBindingsBuilder() {
    this.commandInterceptors = new HashMap<>();
    this.handlers = new HashMap<>();
    this.mappers = new HashMap<>();
    this.mappingInterceptors = new HashMap<>();
    this.argumentMapperProviders = new HashSet<>();
  }

  @Nonnull
  public static CommandBindingsBuilder create() {
    return new CommandBindingsBuilder();
  }

  @Nonnull
  public <T> CommandBindingsBuilder bindMapper(@Nonnull Class<T> type, @Nonnull ArgumentMapper<T> mapper) {
    return bindMapper(type, null, mapper);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public <T> CommandBindingsBuilder bindMapper(
      @Nonnull Class<T> type, @Nullable Class<? extends Annotation> annotationType, @Nonnull ArgumentMapper<T> mapper) {
    return bindMapper(TypeToken.of(type), annotationType, mapper);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public final <T> CommandBindingsBuilder bindMapper(@Nonnull TypeToken<T> type, @Nonnull ArgumentMapper<T> mapper) {
    return bindMapper(type, null, mapper);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public final <T> CommandBindingsBuilder bindMapper(
      @Nonnull TypeToken<T> type, @Nullable Class<? extends Annotation> annotationType, @Nonnull ArgumentMapper<T> mapper) {
    checkNotNull(type);
    checkNotNull(mapper);
    mappers.put(TypeKey.get(type, annotationType), mapper);
    return this;
  }

  @Nonnull
  protected CommandBindingsBuilder bindMapperProvider(@Nonnull ArgumentMapperProvider provider) {
    checkNotNull(provider);
    argumentMapperProviders.add(provider);
    return this;
  }

  @Nonnull
  protected <T extends Throwable> CommandBindingsBuilder bindHandler(
      @Nonnull Class<T> exceptionType, @Nonnull ExceptionHandler<T> handler) {
    checkNotNull(exceptionType);
    checkNotNull(handler);
    handlers.put(exceptionType, handler);
    return this;
  }

  @Nonnull
  protected <T extends Annotation> CommandBindingsBuilder bindCommandInterceptor(
      @Nonnull Class<T> annotationType, @Nonnull CommandInterceptor<T> interceptor) {
    checkNotNull(annotationType);
    checkNotNull(interceptor);
    commandInterceptors.put(annotationType, interceptor);
    return this;
  }

  @Nonnull
  protected <T extends Annotation> CommandBindingsBuilder bindMappingInterceptor(
      @Nonnull Class<T> annotationType, @Nonnull MappingInterceptor<T> interceptor) {
    checkNotNull(annotationType);
    checkNotNull(interceptor);
    mappingInterceptors.put(annotationType, interceptor);
    return this;
  }

  @Nonnull
  public CommandBindings build() {
    return new CommandBindings(commandInterceptors, handlers, mappers, mappingInterceptors, argumentMapperProviders);
  }

}
