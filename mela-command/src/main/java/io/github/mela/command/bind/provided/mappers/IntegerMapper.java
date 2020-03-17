package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class IntegerMapper<T extends Number> extends NumberMapper<T> {

  private final BiFunction<String, Integer, T> converter;

  public IntegerMapper(@Nonnull Class<T> type, @Nonnull BiFunction<String, Integer, T> converter) {
    super(type);
    this.converter = checkNotNull(converter);
  }

  @Override
  protected T convert(String string, ContextMap mappingContext) {
    return converter.apply(string, mappingContext.get(int.class, "base").orElse(10));
  }
}
