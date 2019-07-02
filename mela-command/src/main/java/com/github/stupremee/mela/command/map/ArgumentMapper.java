package com.github.stupremee.mela.command.map;

import com.github.stupremee.mela.command.CommandContext;

public interface ArgumentMapper<T> {

  T map(Object argument, CommandContext context); // TODO: 24.06.2019 replace with actual logic

  static <T> ArgumentMapper<T> singleton(T instance) {
    return null;
  }
}
