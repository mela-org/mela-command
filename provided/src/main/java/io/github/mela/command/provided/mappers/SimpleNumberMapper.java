package io.github.mela.command.provided.mappers;

import static com.google.common.base.Preconditions.checkNotNull;


import java.util.function.Function;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class SimpleNumberMapper<T extends Number> extends NumberMapper<T> {

  private final Function<String, T> converter;

  public SimpleNumberMapper(@Nonnull Class<T> type, @Nonnull Function<String, T> converter) {
    super(type);
    this.converter = checkNotNull(converter);
  }

  @Override
  protected T convert(String input) {
    return converter.apply(input);
  }
}
