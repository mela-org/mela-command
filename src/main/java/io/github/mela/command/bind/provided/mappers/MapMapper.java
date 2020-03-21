package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MapMapper<T extends Map<? super Object, ? super Object>> implements ArgumentMapper<T> {

  private final Supplier<T> factory;
  private final MappingProcessor keyProcessor;
  private final MappingProcessor valueProcessor;

  public MapMapper(Supplier<T> factory, MappingProcessor keyProcessor, MappingProcessor valueProcessor) {
    this.factory = factory;
    this.keyProcessor = keyProcessor;
    this.valueProcessor = valueProcessor;
  }

  @Override
  public T map(@Nonnull Arguments arguments, @Nonnull CommandContext commandContext) throws Throwable {
    Arguments mapArguments = arguments.peek() == '{'
        ? Arguments.of(arguments.nextScope('{', '}'))
        : arguments;
    T map = factory.get();
    while (mapArguments.hasNext()) {
      Object key = keyProcessor.process(mapArguments, commandContext);
      if (!mapArguments.hasNext()) {
        throw new MappingProcessException("Reached end of arguments while parsing map; Missing value for key " + key);
      }
      Object value = valueProcessor.process(mapArguments, commandContext);
      map.put(key, value);
    }
    return map;
  }
}
