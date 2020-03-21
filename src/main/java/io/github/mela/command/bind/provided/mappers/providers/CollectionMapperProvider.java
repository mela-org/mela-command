package io.github.mela.command.bind.provided.mappers.providers;

import static com.google.common.base.Preconditions.checkNotNull;


import io.github.mela.command.bind.AnnotatedTypes;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.bind.provided.mappers.CollectingMapper;
import java.lang.reflect.AnnotatedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CollectionMapperProvider<T extends Collection<? super Object>>
    implements ArgumentMapperProvider {

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
    TargetType contentType = TargetType.of(
        Arrays.stream(AnnotatedTypes.getActualTypeArguments(listType))
            .findFirst()
            .orElse(AnnotatedTypes.STRING)
    );
    return new CollectingMapper(MappingProcessor.fromTargetType(bindings, contentType),
        Collectors.toCollection((Supplier) factory));
  }

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return type.getTypeToken().getRawType().isAssignableFrom(this.type);
  }
}
