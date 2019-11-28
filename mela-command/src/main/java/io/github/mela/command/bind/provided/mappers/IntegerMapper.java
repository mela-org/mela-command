package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.MappingContextKey;
import io.github.mela.command.core.CommandContext;

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
  protected T convert(String argument, CommandContext context) {
    return converter.apply(argument, context.get(int.class, MappingContextKey.of("base", this)).orElse(10));
  }
}
