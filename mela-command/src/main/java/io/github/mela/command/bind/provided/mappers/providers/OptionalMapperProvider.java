package io.github.mela.command.bind.provided.mappers.providers;

import io.github.mela.command.bind.AnnotatedTypes;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.bind.provided.mappers.OptionalMapper;

import javax.annotation.Nonnull;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class OptionalMapperProvider implements ArgumentMapperProvider {

  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    AnnotatedType optionalType = type.getAnnotatedType();
    TargetType contentType = TargetType.create(optionalType instanceof AnnotatedParameterizedType
        ? ((AnnotatedParameterizedType) optionalType).getAnnotatedActualTypeArguments()[0]
        : AnnotatedTypes.STRING);
    MappingProcessor processor = MappingProcessor.fromTargetType(bindings, contentType);
    return new OptionalMapper<>(processor, Optional::of, Optional::empty);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return type.getTypeToken().getRawType() == Optional.class;
  }
}
