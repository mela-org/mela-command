package io.github.mela.command.bind.provided.mappers;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class NumberFormatMapper<T extends Number> extends NumberMapper<T> {

  private final NumberFormat format;
  private final Function<BigDecimal, T> fromNumberFunction;

  public NumberFormatMapper(
      @Nonnull Class<T> type, @Nonnull NumberFormat format, @Nonnull Function<BigDecimal, T> fromNumberFunction) {
    super(type);
    this.format = format;
    this.fromNumberFunction = checkNotNull(fromNumberFunction);
  }

  @Override
  protected T convert(String input) {
    ParsePosition pos = new ParsePosition(0);
    Number number = format.parse(input, pos);
    BigDecimal result = number instanceof BigDecimal
        ? (BigDecimal) number
        : BigDecimal.valueOf(number.doubleValue());
    T value = fromNumberFunction.apply(result);
    if (pos.getIndex() != input.length() || !number.equals(value)) {
      throw new NumberFormatException("For input string: \"" + input + "\"");
    }
    return value;
  }
}
