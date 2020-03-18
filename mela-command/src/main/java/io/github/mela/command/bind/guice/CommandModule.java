package io.github.mela.command.bind.guice;

import com.google.common.reflect.TypeToken;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import io.github.mela.command.bind.CommandInterceptor;
import io.github.mela.command.bind.ExceptionHandler;
import io.github.mela.command.bind.TypeKey;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingInterceptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("rawtypes")
public abstract class CommandModule extends AbstractModule {

  private MapBinder mapperBinder;
  private MapBinder mappingInterceptorBinder;
  private MapBinder commandInterceptorBinder;
  private MapBinder handlerBinder;
  private Multibinder<ArgumentMapperProvider> mapperProviderBinder;
  private CommandBinder commandBinder;

  @Override
  protected final void configure() {
    this.mapperBinder = MapBinder.newMapBinder(binder(), TypeKey.class, ArgumentMapper.class);
    this.mappingInterceptorBinder = MapBinder.newMapBinder(binder(), Class.class, MappingInterceptor.class);
    this.commandInterceptorBinder = MapBinder.newMapBinder(binder(), Class.class, CommandInterceptor.class);
    this.handlerBinder = MapBinder.newMapBinder(binder(), Class.class, ExceptionHandler.class);
    this.mapperProviderBinder = Multibinder.newSetBinder(binder(), ArgumentMapperProvider.class);
    this.commandBinder = CommandBinder.create(binder());
    configureModule();
  }

  protected void configureModule() {};

  @Nonnull
  protected final <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(@Nonnull Class<T> type) {
    return bindMapper(type, null);
  }

  @Nonnull
  protected final <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(
      @Nonnull Class<T> type, @Nullable Class<? extends Annotation> annotationType) {
    return bindMapper((Type) type, annotationType);
  }

  @Nonnull
  protected final <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(@Nonnull TypeLiteral<T> type) {
    return bindMapper(type.getType(), null);
  }

  @Nonnull
  protected final <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(
      @Nonnull TypeLiteral<T> type, @Nullable Class<? extends Annotation> annotationType) {
    return bindMapper(type.getType(), annotationType);
  }

  @SuppressWarnings({"unchecked", "UnstableApiUsage"})
  private <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(
      Type type, Class<? extends Annotation> annotationType) {
    checkNotNull(type);
    return (LinkedBindingBuilder<ArgumentMapper<? extends T>>)
        bindMapper((TypeKey) TypeKey.get(TypeToken.of(type), annotationType));
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  protected final <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(@Nonnull TypeKey<T> key) {
    checkNotNull(key);
    return ((MapBinder<TypeKey, ArgumentMapper<? extends T>>) mapperBinder)
        .addBinding(key);
  }

  @Nonnull
  protected final LinkedBindingBuilder<ArgumentMapperProvider> bindMapperProvider() {
    return mapperProviderBinder.addBinding();
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  protected final <T extends Throwable> LinkedBindingBuilder<ExceptionHandler<? extends T>> bindHandler(
      @Nonnull Class<T> exceptionType) {
    checkNotNull(exceptionType);
    return ((MapBinder<Class<?>, ExceptionHandler<? extends T>>) handlerBinder)
        .addBinding(exceptionType);
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  protected final <T extends Annotation> LinkedBindingBuilder<CommandInterceptor<? extends T>> bindCommandInterceptor(
      @Nonnull Class<T> annotationType) {
    checkNotNull(annotationType);
    return ((MapBinder<Class<?>, CommandInterceptor<? extends T>>) commandInterceptorBinder)
        .addBinding(annotationType);
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  protected final <T extends Annotation> LinkedBindingBuilder<MappingInterceptor<? extends T>> bindMappingInterceptor(
      @Nonnull Class<T> annotationType) {
    checkNotNull(annotationType);
    return ((MapBinder<Class<?>, MappingInterceptor<? extends T>>) mappingInterceptorBinder)
        .addBinding(annotationType);
  }

  @Nonnull
  protected final CommandBindingNode rootNode() {
    return commandBinder.root();
  }
}
