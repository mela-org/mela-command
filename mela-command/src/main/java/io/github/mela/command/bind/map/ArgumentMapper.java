package io.github.mela.command.bind.map;

import io.github.mela.command.bind.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.util.NoSuchElementException;

@FunctionalInterface
public interface ArgumentMapper<T> {

  T map(@Nonnull String argument, @Nonnull ContextMap commandContext, @Nonnull ContextMap mappingContext);

  default String prepare(Arguments arguments) {
    try {
      return arguments.next();
    } catch (NoSuchElementException e) {
      throw new MissingArgumentException("Missing argument; reached end of arguments during mapping process (arguments: \""
          + arguments + "\")", e);
    }
  }

  @Nonnull
  static <T> ArgumentMapper<T> singleton(T instance) {
    return ((argument, commandContext, mappingContext) -> instance);
  }
}
