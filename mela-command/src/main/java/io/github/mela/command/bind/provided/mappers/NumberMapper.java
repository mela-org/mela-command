package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessException;
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
  public T map(@Nonnull String argument, @Nonnull ContextMap commandContext, @Nonnull ContextMap mappingContext) {
    try {
      return convert(argument, mappingContext);
    } catch (NumberFormatException e) {
      throw new MappingProcessException("Invalid argument: could not convert \""
          + argument + "\" to a number (" + type.getSimpleName() + ")", e);
    }
  }

  protected abstract T convert(String argument, ContextMap mappingContext);

}
