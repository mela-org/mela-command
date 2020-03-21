package io.github.mela.command.provided.mappers.providers;

import com.google.common.primitives.Primitives;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.provided.Base;
import io.github.mela.command.bind.IllegalTargetTypeError;
import io.github.mela.command.provided.Localized;
import io.github.mela.command.bind.PreconditionError;
import io.github.mela.command.provided.mappers.NumberFormatMapper;
import io.github.mela.command.provided.mappers.SimpleNumberMapper;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class NumberMapperProvider implements ArgumentMapperProvider {

  private static final Map<Type, BiFunction<String, Integer, ? extends Number>> CONVERTERS =
      Map.of(Byte.class, Byte::valueOf,
          Short.class, Short::valueOf,
          Integer.class, Integer::valueOf,
          Long.class, Long::valueOf,
          BigInteger.class, BigInteger::new,
          Float.class, (s, $) -> Float.valueOf(s),
          Double.class, (s, $) -> Double.valueOf(s),
          BigDecimal.class, (s, $) -> new BigDecimal(s));

  private static final Map<Type, Function<BigDecimal, ? extends Number>> FROM_BIGDECIMAL =
      Map.of(Byte.class, Number::byteValue,
          Short.class, Number::shortValue,
          Integer.class, Number::intValue,
          Long.class, Number::longValue,
          BigInteger.class, BigDecimal::toBigIntegerExact,
          Float.class, Number::floatValue,
          Double.class, Number::doubleValue,
          BigDecimal.class, Function.identity());

  private static final Map<Type, Function<? super Number, BigDecimal>> TO_BIGDECIMAL =
      Map.of(Byte.class, (n) -> BigDecimal.valueOf(n.byteValue()),
          Short.class, (n) -> BigDecimal.valueOf(n.shortValue()),
          Integer.class, (n) -> BigDecimal.valueOf(n.intValue()),
          Long.class, (n) -> BigDecimal.valueOf(n.longValue()),
          BigInteger.class, (n) -> new BigDecimal((BigInteger) n),
          Float.class, (n) -> BigDecimal.valueOf(n.floatValue()),
          Double.class, (n) -> BigDecimal.valueOf(n.doubleValue()),
          BigDecimal.class, (n) -> (BigDecimal) n);

  private static final Set<Type> FLOATING_POINT = Set.of(Float.class, Double.class, BigDecimal.class);

  @SuppressWarnings( {"unchecked", "rawtypes"})
  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    Class numberType = Primitives.wrap((Class) type.getType());
    Base base = type.getAnnotatedType().getAnnotation(Base.class);
    if (base != null && FLOATING_POINT.contains(numberType)) {
      throw new IllegalTargetTypeError(numberType, Base.class);
    }

    int radix = base != null ? base.value() : 10;
    if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
      throw new PreconditionError(
          "Illegal @Base annotation value for target type " + type + ": " + radix
              + " (base must not be greater than Character.MAX_RADIX " +
              "or smaller than Character.MIN_RADIX)."
      );
    }

    Localized localized = type.getAnnotatedType().getAnnotation(Localized.class);
    if (localized != null) {
      if (!FLOATING_POINT.contains(numberType) && radix != 10) {
        throw new PreconditionError("Illegal @Localized annotation usage for target type " + type
            + "; localized integer types must be in base 10.");
      }
      Locale locale = Locale.forLanguageTag(localized.value());
      NumberFormat format = NumberFormat.getInstance(locale);
      if (!FLOATING_POINT.contains(numberType)) {
        format.setParseIntegerOnly(true);
      }
      if (format instanceof DecimalFormat) {
        ((DecimalFormat) format).setParseBigDecimal(true);
      }
      Function fromBigDecimalFunction = FROM_BIGDECIMAL.get(numberType);
      Function toBigDecimalFunction = TO_BIGDECIMAL.get(numberType);
      return new NumberFormatMapper(numberType, format,
          fromBigDecimalFunction, toBigDecimalFunction);
    } else {
      BiFunction converter = CONVERTERS.get(numberType);
      return new SimpleNumberMapper(numberType, (s) -> converter.apply(s, radix));
    }
  }

  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return type.getType() instanceof Class<?>
        && CONVERTERS.containsKey(Primitives.wrap((Class<?>) type.getType()));
  }

}
