package io.github.mela.command.bind.provided.mappers;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class SimpleNumberMapper<T extends Number> extends NumberMapper<T> {

  private final Function<String, T> converter;

  public SimpleNumberMapper(@Nonnull Class<T> type, @Nonnull Function<String, T> converter) {
    super(type);
    this.converter = converter;
  }

  @Override
  protected T convert(String input) {
    return converter.apply(input);
  }
}
