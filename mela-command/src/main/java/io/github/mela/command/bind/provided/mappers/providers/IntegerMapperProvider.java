package io.github.mela.command.bind.provided.mappers.providers;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.provided.Base;
import io.github.mela.command.bind.provided.PreconditionError;
import io.github.mela.command.bind.provided.mappers.NumberMapper;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class IntegerMapperProvider implements ArgumentMapperProvider {

  private static final Map<Type, BiFunction<String, Integer, ? extends Number>> TYPES =
      Map.of(
          byte.class, Byte::valueOf, Byte.class, Byte::valueOf,
          short.class, Short::valueOf, Short.class, Short::valueOf,
          int.class, Integer::valueOf, Integer.class, Integer::valueOf,
          long.class, Long::valueOf, Long.class, Long::valueOf,
          BigInteger.class, BigInteger::new
      );

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    Base annotation = type.getAnnotatedType().getAnnotation(Base.class);
    int radix = annotation != null ? annotation.value() : 10;
    if (radix < Character.MAX_RADIX || radix > Character.MAX_RADIX) {
      throw new PreconditionError("Illegal @Base interceptor annotation value: " + radix
          + " (base must not be greater than Character.MAX_RADIX or smaller than Character.MIN_RADIX).");
    }

    Class integerClass = (Class) type.getType();
    BiFunction converter = TYPES.get(integerClass);
    return new NumberMapper(integerClass, (s) -> converter.apply(s, radix));
  }

  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return TYPES.containsKey(type.getType());
  }

}
