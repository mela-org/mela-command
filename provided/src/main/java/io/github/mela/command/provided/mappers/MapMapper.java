package io.github.mela.command.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.core.ArgumentException;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MapMapper<T extends Map<? super Object, ? super Object>> implements ArgumentMapper<T> {

  private final Supplier<T> factory;
  private final MappingProcessor keyProcessor;
  private final MappingProcessor valueProcessor;

  public MapMapper(
      Supplier<T> factory, MappingProcessor keyProcessor, MappingProcessor valueProcessor) {
    this.factory = factory;
    this.keyProcessor = keyProcessor;
    this.valueProcessor = valueProcessor;
  }

  @Override
  public T map(@Nonnull CommandArguments arguments, @Nonnull CommandContext commandContext)
      throws Throwable {
    CommandArguments mapArguments = arguments.peek() == '{'
        ? CommandArguments.of(arguments.nextScope('{', '}'))
        : arguments;
    T map = factory.get();
    while (mapArguments.hasNext()) {
      Object key = keyProcessor.process(mapArguments, commandContext);
      if (!mapArguments.hasNext()) {
        throw ArgumentException.create(
            "Reached end of arguments while parsing map; Missing value for key "
                + key, arguments
        );
      }
      Object value = valueProcessor.process(mapArguments, commandContext);
      map.put(key, value);
    }
    return map;
  }
}
