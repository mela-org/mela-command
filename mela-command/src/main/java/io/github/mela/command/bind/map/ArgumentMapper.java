package io.github.mela.command.bind.map;

import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;

public interface ArgumentMapper<T> {

  T map(@Nonnull String argument, @Nonnull CommandContext context); // TODO: 24.06.2019 replace with actual logic

  default String prepare(ArgumentChain argumentChain) {
    if (argumentChain.hasNext()) {
      return argumentChain.next();
    } else {
      throw new RuntimeException("Missing argument");
    }
  }

  @Nonnull
  static <T> ArgumentMapper<T> singleton(T instance) {
    return ((argument, context) -> instance);
  }
}
