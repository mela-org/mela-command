package io.github.mela.command.provided.mappers.providers;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Primitives;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.provided.Base;
import io.github.mela.command.provided.Localized;
import io.github.mela.command.bind.IllegalTargetTypeError;
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

  private static final Map<Type, BiFunction<String, Integer, ? extends Number>> CONVERTERS;
  private static final Map<Type, Function<BigDecimal, ? extends Number>> FROM_BIGDECIMAL;
  private static final Map<Type, Function<? super Number, BigDecimal>> TO_BIGDECIMAL;
  private static final Set<Type> FLOATING_POINT;

  static {
    CONVERTERS = ImmutableMap.<Type, BiFunction<String, Integer, ? extends Number>>builder()
        .put(Byte.class, Byte::valueOf)
        .put(Short.class, Short::valueOf)
        .put(Integer.class, Integer::valueOf)
        .put(Long.class, Long::valueOf)
        .put(BigInteger.class, BigInteger::new)
        .put(Float.class, (s, r) -> Float.valueOf(s))
        .put(Double.class, (s, r) -> Double.valueOf(s))
        .put(BigDecimal.class, (s, r) -> new BigDecimal(s))
        .build();
    FROM_BIGDECIMAL = ImmutableMap.<Type, Function<BigDecimal, ? extends Number>>builder()
        .put(Byte.class, Number::byteValue)
        .put(Short.class, Number::shortValue)
        .put(Integer.class, Number::intValue)
        .put(Long.class, Number::longValue)
        .put(BigInteger.class, BigDecimal::toBigIntegerExact)
        .put(Float.class, Number::floatValue)
        .put(Double.class, Number::doubleValue)
        .put(BigDecimal.class, Function.identity())
        .build();
    TO_BIGDECIMAL = ImmutableMap.<Type, Function<? super Number, BigDecimal>>builder()
        .put(Byte.class, (n) -> BigDecimal.valueOf(n.byteValue()))
        .put(Short.class, (n) -> BigDecimal.valueOf(n.shortValue()))
        .put(Integer.class, (n) -> BigDecimal.valueOf(n.intValue()))
        .put(Long.class, (n) -> BigDecimal.valueOf(n.longValue()))
        .put(BigInteger.class, (n) -> new BigDecimal((BigInteger) n))
        .put(Float.class, (n) -> BigDecimal.valueOf(n.floatValue()))
        .put(Double.class, (n) -> BigDecimal.valueOf(n.doubleValue()))
        .put(BigDecimal.class, (n) -> (BigDecimal) n)
        .build();
    FLOATING_POINT = ImmutableSet.of(Float.class, Double.class, BigDecimal.class);
  }



  @SuppressWarnings({"unchecked", "rawtypes"})
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
              + " (base must not be greater than Character.MAX_RADIX "
              + "or smaller than Character.MIN_RADIX)."
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
