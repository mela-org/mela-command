package io.github.mela.command.provided.mappers.providers;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.provided.mappers.CollectingMapper;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.Array;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ArrayMapperProvider implements ArgumentMapperProvider {


  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    AnnotatedArrayType arrayType = (AnnotatedArrayType) type.getAnnotatedType();
    TargetType componentType = TargetType.of(arrayType.getAnnotatedGenericComponentType());
    Class<?> rawComponentType = componentType.getTypeToken().getRawType();
    return new CollectingMapper<>(MappingProcessor.fromTargetType(bindings, componentType),
        Collectors.collectingAndThen(Collectors.toList(),
            (l) -> l.toArray((Object[]) Array.newInstance(rawComponentType, 0))));
  }

  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return type.getAnnotatedType() instanceof AnnotatedArrayType;
  }
}
