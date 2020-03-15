package io.github.mela.command.bind.provided.mappers.providers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class FlagMapper<T> implements ArgumentMapper<Flag<T>> {

  private final MappingProcessor valueMappingProcessor;

  public FlagMapper(MappingProcessor valueMappingProcessor) {
    this.valueMappingProcessor = valueMappingProcessor;
  }

  @Override
  public Flag<T> map(@Nonnull String argument, @Nonnull ContextMap commandContext, @Nonnull ContextMap mappingContext) {
    valueMappingProcessor.
  }

  @Override
  public String prepare(@Nonnull Arguments arguments) {
    return null;
  }
}
