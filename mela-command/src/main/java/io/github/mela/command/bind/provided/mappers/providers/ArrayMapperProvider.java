package io.github.mela.command.bind.provided.mappers.providers;

import com.google.inject.Singleton;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.bind.provided.mappers.CollectingMapper;

import javax.annotation.Nonnull;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.Array;
import java.util.stream.Collectors;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class ArrayMapperProvider implements ArgumentMapperProvider {


  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    AnnotatedArrayType arrayType = (AnnotatedArrayType) type.getAnnotatedType();
    TargetType componentType = TargetType.create(arrayType.getAnnotatedGenericComponentType());
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
