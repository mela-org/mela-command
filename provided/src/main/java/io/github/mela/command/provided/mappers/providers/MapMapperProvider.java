package io.github.mela.command.provided.mappers.providers;

import static com.google.common.base.Preconditions.checkNotNull;


import io.github.mela.command.bind.AnnotatedTypes;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.provided.mappers.MapMapper;
import java.lang.reflect.AnnotatedType;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MapMapperProvider<T extends Map<? super Object, ? super Object>>
    implements ArgumentMapperProvider {

  private final Class<T> type;
  private final Supplier<T> factory;

  public MapMapperProvider(@Nonnull Class<T> type, @Nonnull Supplier<T> factory) {
    this.type = checkNotNull(type);
    this.factory = checkNotNull(factory);
  }

  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    AnnotatedType mapType = type.getAnnotatedType();
    TargetType keyType;
    TargetType valueType;
    AnnotatedType[] typeArguments = AnnotatedTypes.getActualTypeArguments(mapType);
    if (typeArguments.length == 0) {
      typeArguments = new AnnotatedType[] {AnnotatedTypes.STRING, AnnotatedTypes.STRING};
    }
    keyType = TargetType.of(typeArguments[0]);
    valueType = TargetType.of(typeArguments[1]);
    return new MapMapper<>(
        factory,
        MappingProcessor.fromTargetType(bindings, keyType),
        MappingProcessor.fromTargetType(bindings, valueType)
    );
  }

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return type.getTypeToken().getRawType().isAssignableFrom(this.type);
  }
}
