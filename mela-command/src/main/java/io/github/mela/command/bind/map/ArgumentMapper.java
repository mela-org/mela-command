package io.github.mela.command.bind.map;

import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.util.NoSuchElementException;

public interface ArgumentMapper<T> {

  T map(@Nonnull String argument, @Nonnull CommandContext context);

  default String prepare(ArgumentChain argumentChain) {
    try {
      return argumentChain.next();
    } catch (NoSuchElementException e) {
      throw new MissingArgumentException("Missing argument; reached end of argument chain " + argumentChain, e);
    }
  }

  @Nonnull
  static <T> ArgumentMapper<T> singleton(T instance) {
    return ((argument, context) -> instance);
  }
}
