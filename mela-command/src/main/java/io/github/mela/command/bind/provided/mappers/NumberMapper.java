package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class NumberMapper<T extends Number> implements ArgumentMapper<T> {

  private final Class<T> type;

  public NumberMapper(@Nonnull Class<T> type) {
    this.type = checkNotNull(type);
  }

  @Override
  public T map(@Nonnull Arguments arguments, @Nonnull ContextMap commandContext, @Nonnull ContextMap mappingContext) {
    String next = arguments.nextString();
    try {
      return convert(next, mappingContext);
    } catch (NumberFormatException e) {
      throw new MappingProcessException("Invalid argument: could not convert \""
          + next + "\" to a number (" + type.getSimpleName() + ")", e);
    }
  }

  protected abstract T convert(String string, ContextMap mappingContext);

}
