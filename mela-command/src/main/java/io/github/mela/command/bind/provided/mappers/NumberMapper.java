package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.ArgumentException;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class NumberMapper<T extends Number> implements ArgumentMapper<T> {

  private final Class<T> type;
  private final Function<String, T> converter;

  public NumberMapper(@Nonnull Class<T> type, @Nonnull Function<String, T> converter) {
    this.type = type;
    this.converter = converter;
  }

  @Override
  public T map(@Nonnull String argument, @Nonnull CommandContext context) {
    try {
      return converter.apply(argument);
    } catch (NumberFormatException e) {
      throw new ArgumentException("Invalid argument: could not convert \""
          + argument + "\" to a number (" + type.getSimpleName() + ")", e);
    }
  }

}
