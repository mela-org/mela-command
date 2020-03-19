package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class NumberMapper<T extends Number> implements ArgumentMapper<T> {

  private final Class<T> type;

  protected NumberMapper(@Nonnull Class<T> type) {
    this.type = checkNotNull(type);
  }

  @Override
  public T map(@Nonnull Arguments arguments, @Nonnull CommandContext commandContext) {
    String next = arguments.nextString();
    try {
      return convert(next);
    } catch (NumberFormatException e) {
      throw new MappingProcessException("Invalid argument: could not convert \""
          + next + "\" to a number (" + type + ")", e);
    }
  }

  protected abstract T convert(String input);

}
