package io.github.mela.command.bind;

import io.github.mela.command.CommandContext;

import javax.annotation.Nonnull;

public interface ArgumentMapper<T> {

  T map(@Nonnull String argument, @Nonnull CommandContext context); // TODO: 24.06.2019 replace with actual logic

  @Nonnull
  static <T> ArgumentMapper<T> singleton(T instance) {
    return ((argument, context) -> instance);
  }
}
