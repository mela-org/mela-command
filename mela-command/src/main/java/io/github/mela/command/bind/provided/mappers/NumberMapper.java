package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class NumberMapper<T extends Number> implements ArgumentMapper<T> {

  private final Class<T> type;
  private final Function<String, T> converter;

  public NumberMapper(@Nonnull Class<T> type, Function<String, T> converter) {
    this.type = checkNotNull(type);
    this.converter = converter;
  }

  @Override
  public T map(@Nonnull Arguments arguments, @Nonnull ContextMap commandContext) {
    String next = arguments.nextString();
    try {
      return converter.apply(next);
    } catch (NumberFormatException e) {
      throw new MappingProcessException("Invalid argument: could not convert \""
          + next + "\" to a number (" + type + ")", e);
    }
  }

}
