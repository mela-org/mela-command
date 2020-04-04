package io.github.mela.command.provided.mappers;

import static com.google.common.base.Preconditions.checkNotNull;


import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMappingException;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class NumberMapper<T extends Number> implements ArgumentMapper<T> {

  private final Class<T> type;

  protected NumberMapper(@Nonnull Class<T> type) {
    this.type = checkNotNull(type);
  }

  @Override
  public T map(@Nonnull CommandArguments arguments, @Nonnull CommandContext commandContext) {
    String next = arguments.nextString();
    try {
      return convert(next);
    } catch (NumberFormatException e) {
      throw ArgumentMappingException.create("Invalid argument: could not convert \""
          + next + "\" to a number (" + type.getSimpleName() + ")", type, next, e);
    }
  }

  protected abstract T convert(String input);

}
