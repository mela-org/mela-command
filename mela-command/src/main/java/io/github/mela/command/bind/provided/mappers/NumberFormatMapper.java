package io.github.mela.command.bind.provided.mappers;

import javax.annotation.Nonnull;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class NumberFormatMapper<T extends Number> extends NumberMapper<T> {

  private final NumberFormat format;
  private final Function<Number, T> fromNumberFunction;

  public NumberFormatMapper(
      @Nonnull Class<T> type, @Nonnull Locale locale, @Nonnull Function<Number, T> fromNumberFunction) {
    super(type);
    this.format = NumberFormat.getInstance(locale);
    this.fromNumberFunction = checkNotNull(fromNumberFunction);
  }

  @Override
  protected T convert(String input) {
    ParsePosition pos = new ParsePosition(0);
    Number number = format.parse(input, pos);
    Number value = fromNumberFunction.apply(number);
    if (pos.getIndex() != input.length() || !number.equals(value)) {
      throw new NumberFormatException("For input string: \"" + input + "\"");
    }
    return fromNumberFunction.apply(value);
  }
}
