package io.github.mela.command.bind.map;

import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@FunctionalInterface
public interface ArgumentMapper<T> {

  @Nonnull
  static <T> ArgumentMapper<T> singleton(@Nullable T instance) {
    return ((argument, commandContext) -> instance);
  }

  T map(@Nonnull CommandArguments arguments, @Nonnull CommandContext commandContext) throws Throwable;
}
