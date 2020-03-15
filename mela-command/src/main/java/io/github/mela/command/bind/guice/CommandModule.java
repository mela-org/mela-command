package io.github.mela.command.bind.guice;

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
import io.github.mela.command.bind.parameter.ParameterMarker;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import static com.google.common.base.Preconditions.checkArgument;

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
  protected void configure() {
    this.mapperBinder = MapBinder.newMapBinder(binder(), TypeKey.class, ArgumentMapper.class);
    this.mappingInterceptorBinder = MapBinder.newMapBinder(binder(), Class.class, MappingInterceptor.class);
    this.commandInterceptorBinder = MapBinder.newMapBinder(binder(), Class.class, CommandInterceptor.class);
    this.handlerBinder = MapBinder.newMapBinder(binder(), Class.class, ExceptionHandler.class);
    this.mapperProviderBinder = Multibinder.newSetBinder(binder(), ArgumentMapperProvider.class);
    this.commandBinder = CommandBinder.create(binder());
  }

  @Nonnull
  protected <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(@Nonnull Class<T> type) {
    return bindMapper(type, null);
  }

  @Nonnull
  protected <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(@Nonnull Class<T> type, @Nullable Class<? extends Annotation> annotationType) {
    return bindMapper((Type) type, annotationType);
  }

  @Nonnull
  protected <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(@Nonnull TypeLiteral<T> type) {
    return bindMapper(type.getType(), null);
  }

  @Nonnull
  protected <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(@Nonnull TypeLiteral<T> type, @Nullable Class<? extends Annotation> annotationType) {
    return bindMapper(type.getType(), annotationType);
  }

  @SuppressWarnings("unchecked")
  private <T> LinkedBindingBuilder<ArgumentMapper<? extends T>> bindMapper(
      @Nonnull Type type, Class<? extends Annotation> annotationType) {
    if (annotationType != null) {
      checkArgument(annotationType.isAnnotationPresent(ParameterMarker.class),
          "Annotation " + annotationType + " does not have the @ParameterMarker annotation");
    }
    return ((MapBinder<TypeKey, ArgumentMapper<? extends T>>) mapperBinder)
        .addBinding(TypeKey.get(type, annotationType));
  }

  @Nonnull
  protected LinkedBindingBuilder<ArgumentMapperProvider> bindMapperProvider() {
    return mapperProviderBinder.addBinding();
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  protected <T extends Throwable> LinkedBindingBuilder<ExceptionHandler<? extends T>> bindHandler(@Nonnull Class<T> exceptionType) {
    return ((MapBinder<Class<?>, ExceptionHandler<? extends T>>) handlerBinder)
        .addBinding(exceptionType);
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  protected <T extends Annotation> LinkedBindingBuilder<CommandInterceptor<? extends T>> bindCommandInterceptor(@Nonnull Class<T> annotationType) {
    return ((MapBinder<Class<?>, CommandInterceptor<? extends T>>) commandInterceptorBinder)
        .addBinding(annotationType);
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  protected <T extends Annotation> LinkedBindingBuilder<MappingInterceptor<? extends T>> bindMappingInterceptor(@Nonnull Class<T> annotationType) {
    return ((MapBinder<Class<?>, MappingInterceptor<? extends T>>) mappingInterceptorBinder)
        .addBinding(annotationType);
  }

  @Nonnull
  protected CommandBindingNode rootNode() {
    return commandBinder.root();
  }
}
