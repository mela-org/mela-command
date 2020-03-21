package io.github.mela.command.provided.mappers;

import static com.google.common.base.Preconditions.checkNotNull;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.Function;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class NumberFormatMapper<T extends Number> extends NumberMapper<T> {

  private final NumberFormat format;
  private final Function<BigDecimal, T> fromBigDecimalFunction;
  private final Function<T, BigDecimal> toBigDecimalFunction;

  public NumberFormatMapper(
      @Nonnull Class<T> type,
      @Nonnull NumberFormat format,
      @Nonnull Function<BigDecimal, T> fromBigDecimalFunction,
      @Nonnull Function<T, BigDecimal> toBigDecimalFunction) {
    super(type);
    this.format = format;
    this.fromBigDecimalFunction = checkNotNull(fromBigDecimalFunction);
    this.toBigDecimalFunction = checkNotNull(toBigDecimalFunction);
  }

  @Override
  protected T convert(String input) {
    ParsePosition pos = new ParsePosition(0);
    Number number = format.parse(input, pos);
    BigDecimal result = number instanceof BigDecimal
        ? (BigDecimal) number
        : BigDecimal.valueOf(number.doubleValue());
    T value = fromBigDecimalFunction.apply(result);
    BigDecimal comparable = toBigDecimalFunction.apply(value);
    if (pos.getIndex() != input.length() || !number.equals(comparable)) {
      throw new NumberFormatException("For input string: \"" + input + "\"");
    }
    return value;
  }
}
