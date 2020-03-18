package io.github.mela.command.bind.map;

import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@FunctionalInterface
public interface ArgumentMapper<T> {

  T map(@Nonnull Arguments arguments, @Nonnull ContextMap commandContext) throws Throwable;

  @Nonnull
  static <T> ArgumentMapper<T> singleton(@Nullable T instance) {
    return ((argument, commandContext) -> instance);
  }
}
