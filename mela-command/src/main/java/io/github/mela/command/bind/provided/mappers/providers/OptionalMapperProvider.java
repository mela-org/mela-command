package io.github.mela.command.bind.provided.mappers.providers;

import com.google.inject.Singleton;
import io.github.mela.command.bind.AnnotatedTypes;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.bind.provided.mappers.OptionalMapper;

import javax.annotation.Nonnull;
import java.lang.reflect.AnnotatedType;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class OptionalMapperProvider implements ArgumentMapperProvider {

  private static final Map<Class<?>, Function<AnnotatedType, AnnotatedType>> OPTIONAL_CONTENT_TYPES
      = Map.of(Optional.class, (type) ->
          AnnotatedTypes.getActualTypeArguments(type).stream().findFirst().orElse(AnnotatedTypes.STRING),
      OptionalInt.class, ($) -> AnnotatedTypes.fromType(int.class),
      OptionalLong.class, ($) -> AnnotatedTypes.fromType(long.class),
      OptionalDouble.class, ($) -> AnnotatedTypes.fromType(double.class));

  private static final Map<Class<?>, Function<? super Object, ?>> WRAPPER_FUNCTIONS
      = Map.of(Optional.class, Optional::of,
      OptionalInt.class, (o) -> OptionalInt.of((int) o),
      OptionalLong.class, (o) -> OptionalLong.of((long) o),
      OptionalDouble.class, (o) -> OptionalDouble.of((double) o));

  private static final Map<Class<?>, Supplier<?>> EMPTY_FUNCTIONS
      = Map.of(Optional.class, Optional::empty,
      OptionalInt.class, OptionalInt::empty,
      OptionalLong.class, OptionalLong::empty,
      OptionalDouble.class, OptionalDouble::empty);

  @SuppressWarnings({"UnstableApiUsage", "unchecked", "rawtypes"})
  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    AnnotatedType optionalType = type.getAnnotatedType();
    Class<?> rawType = type.getTypeToken().getRawType();
    AnnotatedType contentType = OPTIONAL_CONTENT_TYPES.get(rawType).apply(optionalType);
    MappingProcessor processor = MappingProcessor.fromTargetType(bindings, TargetType.create(contentType));
    return new OptionalMapper(processor, WRAPPER_FUNCTIONS.get(rawType), EMPTY_FUNCTIONS.get(rawType));
  }

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return OPTIONAL_CONTENT_TYPES.containsKey(type.getTypeToken().getRawType());
  }
}
