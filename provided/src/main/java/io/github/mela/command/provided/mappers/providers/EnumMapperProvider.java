package io.github.mela.command.provided.mappers.providers;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.provided.mappers.EnumMapper;
import java.lang.reflect.Type;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class EnumMapperProvider implements ArgumentMapperProvider {


  @SuppressWarnings({"unchecked", "rawtypes"})
  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    Class enumType = (Class) type.getType();
    return new EnumMapper(enumType);
  }

  @Override
  public boolean canProvideFor(@Nonnull TargetType targetType) {
    Type type = targetType.getType();
    if (!(type instanceof Class<?>) || type == Enum.class) {
      return false;
    }
    Class<?> clazz = (Class<?>) type;
    return Enum.class.isAssignableFrom(clazz);
  }
}
