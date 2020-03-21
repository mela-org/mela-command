package io.github.mela.command.bind.provided.mappers;

import static com.google.common.base.Preconditions.checkNotNull;


import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessor;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.CommandContext;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class OptionalMapper<T> implements ArgumentMapper<T> {

  private final MappingProcessor subProcessor;
  private final Function<? super Object, T> successFunction;
  private final Supplier<T> optionalSupplier;

  public OptionalMapper(
      @Nonnull MappingProcessor subProcessor,
      @Nonnull Function<? super Object, T> successFunction,
      @Nonnull Supplier<T> optionalSupplier
  ) {
    this.subProcessor = checkNotNull(subProcessor);
    this.successFunction = checkNotNull(successFunction);
    this.optionalSupplier = checkNotNull(optionalSupplier);
  }

  @Override
  public T map(@Nonnull Arguments arguments, @Nonnull CommandContext commandContext) {
    try {
      Object mapped = subProcessor.process(arguments, commandContext);
      return successFunction.apply(mapped);
    } catch (Throwable throwable) {
      return optionalSupplier.get();
    }
  }
}
