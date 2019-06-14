package com.github.stupremee.mela.command.providers;

import com.sk89q.intake.parametric.Provider;

public interface ConsumingProvider<T> extends Provider<T> {
  @Override
  default boolean isProvided() {
    return false;
  }
}
