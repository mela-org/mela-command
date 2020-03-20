package io.github.mela.command.bind;

import com.google.common.reflect.TypeToken;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingInterceptor;

import javax.annotation.CheckReturnValue;
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
@SuppressWarnings("rawtypes")
public final class BindingsBuilder {

  private final Map<Class, CommandInterceptor> commandInterceptors;
  private final Map<Class, ExceptionHandler> handlers;
  private final Map<TypeKey, ArgumentMapper> mappers;
  private final Map<Class, MappingInterceptor> mappingInterceptors;
  private final Set<ArgumentMapperProvider> argumentMapperProviders;

  private BindingsBuilder() {
    this.commandInterceptors = new HashMap<>();
    this.handlers = new HashMap<>();
    this.mappers = new HashMap<>();
    this.mappingInterceptors = new HashMap<>();
    this.argumentMapperProviders = new HashSet<>();
  }

  @Nonnull
  public static BindingsBuilder create() {
    return new BindingsBuilder();
  }

  @Nonnull
  public <T> BindingsBuilder bindMapper(@Nonnull Class<T> type, @Nonnull ArgumentMapper<T> mapper) {
    return bindMapper(type, null, mapper);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public <T> BindingsBuilder bindMapper(
      @Nonnull Class<T> type, @Nullable Class<? extends Annotation> annotationType, @Nonnull ArgumentMapper<T> mapper) {
    return bindMapper(TypeToken.of(type), annotationType, mapper);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public <T> BindingsBuilder bindMapper(@Nonnull TypeToken<T> type, @Nonnull ArgumentMapper<T> mapper) {
    return bindMapper(type, null, mapper);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public <T> BindingsBuilder bindMapper(
      @Nonnull TypeToken<T> type, @Nullable Class<? extends Annotation> annotationType, @Nonnull ArgumentMapper<T> mapper) {
    checkNotNull(type);
    checkNotNull(mapper);
    mappers.put(TypeKey.get(type, annotationType), mapper);
    return this;
  }

  @Nonnull
  public BindingsBuilder bindMapperProvider(@Nonnull ArgumentMapperProvider provider) {
    checkNotNull(provider);
    argumentMapperProviders.add(provider);
    return this;
  }

  @Nonnull
  public <T extends Throwable> BindingsBuilder bindHandler(
      @Nonnull Class<T> exceptionType, @Nonnull ExceptionHandler<T> handler) {
    checkNotNull(exceptionType);
    checkNotNull(handler);
    handlers.put(exceptionType, handler);
    return this;
  }

  @Nonnull
  public <T extends Annotation> BindingsBuilder bindCommandInterceptor(
      @Nonnull Class<T> annotationType, @Nonnull CommandInterceptor<T> interceptor) {
    checkNotNull(annotationType);
    checkNotNull(interceptor);
    commandInterceptors.put(annotationType, interceptor);
    return this;
  }

  @Nonnull
  public <T extends Annotation> BindingsBuilder bindMappingInterceptor(
      @Nonnull Class<T> annotationType, @Nonnull MappingInterceptor<T> interceptor) {
    checkNotNull(annotationType);
    checkNotNull(interceptor);
    mappingInterceptors.put(annotationType, interceptor);
    return this;
  }

  @Nonnull
  public BindingsBuilder bindAll(@Nonnull BindingsBuilder other) {
    checkNotNull(other);
    this.mappers.putAll(other.mappers);
    this.argumentMapperProviders.addAll(other.argumentMapperProviders);
    this.handlers.putAll(other.handlers);
    this.commandInterceptors.putAll(other.commandInterceptors);
    this.mappingInterceptors.putAll(other.mappingInterceptors);
    return this;
  }

  @CheckReturnValue
  @Nonnull
  public CommandBindings build() {
    return new CommandBindings(commandInterceptors, handlers, mappers, mappingInterceptors, argumentMapperProviders);
  }

}
