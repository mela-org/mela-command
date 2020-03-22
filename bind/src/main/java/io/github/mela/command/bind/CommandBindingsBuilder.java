package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingInterceptor;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("rawtypes")
public final class CommandBindingsBuilder {

  private final Map<Class, CommandInterceptor> commandInterceptors;
  private final Map<Class, ExceptionHandler> handlers;
  private final Map<TypeKey, ArgumentMapper> mappers;
  private final Map<Class, MappingInterceptor> mappingInterceptors;
  private final Set<ArgumentMapperProvider> argumentMapperProviders;

  CommandBindingsBuilder() {
    this.commandInterceptors = Maps.newHashMap();
    this.handlers = Maps.newHashMap();
    this.mappers = Maps.newHashMap();
    this.mappingInterceptors = Maps.newHashMap();
    this.argumentMapperProviders = Sets.newLinkedHashSet();
  }

  @Nonnull
  public <T> CommandBindingsBuilder bindMapper(
      @Nonnull Class<T> type, @Nonnull ArgumentMapper<T> mapper) {
    return bindMapper(type, null, mapper);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public <T> CommandBindingsBuilder bindMapper(
      @Nonnull Class<T> type,
      @Nullable Class<? extends Annotation> annotationType,
      @Nonnull ArgumentMapper<T> mapper
  ) {
    return bindMapper(TypeToken.of(type), annotationType, mapper);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public <T> CommandBindingsBuilder bindMapper(
      @Nonnull TypeToken<T> type, @Nonnull ArgumentMapper<T> mapper) {
    return bindMapper(type, null, mapper);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public <T> CommandBindingsBuilder bindMapper(
      @Nonnull TypeToken<T> type,
      @Nullable Class<? extends Annotation> annotationType,
      @Nonnull ArgumentMapper<T> mapper
  ) {
    checkNotNull(type);
    checkNotNull(mapper);
    mappers.put(TypeKey.get(type, annotationType), mapper);
    return this;
  }

  @Nonnull
  public CommandBindingsBuilder bindMapperProvider(@Nonnull ArgumentMapperProvider provider) {
    checkNotNull(provider);
    argumentMapperProviders.add(provider);
    return this;
  }

  @Nonnull
  public <T extends Throwable> CommandBindingsBuilder bindHandler(
      @Nonnull Class<T> exceptionType, @Nonnull ExceptionHandler<T> handler) {
    checkNotNull(exceptionType);
    checkNotNull(handler);
    handlers.put(exceptionType, handler);
    return this;
  }

  @Nonnull
  public <T extends Annotation> CommandBindingsBuilder bindCommandInterceptor(
      @Nonnull Class<T> annotationType, @Nonnull CommandInterceptor<T> interceptor) {
    checkNotNull(annotationType);
    checkNotNull(interceptor);
    commandInterceptors.put(annotationType, interceptor);
    return this;
  }

  @Nonnull
  public <T extends Annotation> CommandBindingsBuilder bindMappingInterceptor(
      @Nonnull Class<T> annotationType, @Nonnull MappingInterceptor<T> interceptor) {
    checkNotNull(annotationType);
    checkNotNull(interceptor);
    mappingInterceptors.put(annotationType, interceptor);
    return this;
  }

  @Nonnull
  public CommandBindingsBuilder bindAll(@Nonnull CommandBindingsBuilder other) {
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
    return CommandBindings.of(commandInterceptors, handlers, mappers,
        mappingInterceptors, argumentMapperProviders);
  }

}
