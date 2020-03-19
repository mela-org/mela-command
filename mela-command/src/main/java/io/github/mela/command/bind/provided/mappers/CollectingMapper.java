package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.util.stream.Collector;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CollectingMapper<A, R> implements ArgumentMapper<R> {

  private final MappingProcessor subProcessor;
  private final Collector<? super Object, A, R> collector;

  public CollectingMapper(@Nonnull MappingProcessor subProcessor,
                          @Nonnull Collector<? super Object, A, R> collector) {

    this.subProcessor = checkNotNull(subProcessor);
    this.collector = checkNotNull(collector);
  }

  @Override
  public R map(@Nonnull Arguments arguments, @Nonnull ContextMap commandContext) throws Throwable {
    Arguments listArguments = arguments.peek() == '['
        ? Arguments.of(arguments.nextScope('[', ']'))
        : arguments;
    A container = collector.supplier().get();
    while (listArguments.hasNext()) {
      Object element = subProcessor.process(listArguments, commandContext);
      collector.accumulator().accept(container, element);
    }
    return collector.finisher().apply(container);
  }


}
