package io.github.mela.command.bind.provided.mappers.providers;

import io.github.mela.command.bind.AnnotatedTypes;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.bind.provided.mappers.CollectingMapper;

import javax.annotation.Nonnull;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CollectionMapperProvider<T extends Collection<? super Object>> implements ArgumentMapperProvider {

  private final Class<T> type;
  private final Supplier<T> factory;

  public CollectionMapperProvider(@Nonnull Class<T> type, @Nonnull Supplier<T> factory) {
    this.type = checkNotNull(type);
    this.factory = checkNotNull(factory);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    AnnotatedType listType = type.getAnnotatedType();
    TargetType contentType = listType instanceof AnnotatedParameterizedType
        ? TargetType.create(((AnnotatedParameterizedType) listType).getAnnotatedActualTypeArguments()[0])
        : TargetType.create(AnnotatedTypes.fromType(String.class));
    return new CollectingMapper(MappingProcessor.fromTargetType(bindings, contentType),
        Collectors.toCollection((Supplier) factory));
  }

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return type.getTypeToken().getRawType().isAssignableFrom(this.type);
  }
}
