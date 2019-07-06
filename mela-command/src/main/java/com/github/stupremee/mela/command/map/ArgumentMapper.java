package com.github.stupremee.mela.command.map;

import com.github.stupremee.mela.command.CommandContext;

import javax.annotation.Nonnull;

public interface ArgumentMapper<T> {

  T map(Object argument, @Nonnull CommandContext context); // TODO: 24.06.2019 replace with actual logic

  @Nonnull
  static <T> ArgumentMapper<T> singleton(T instance) {
    return null;
  }
}
