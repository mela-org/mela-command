package com.github.stupremee.mela.command.provider;

import com.sk89q.intake.parametric.Provider;

public interface NonConsumingProvider<T> extends Provider<T> {

  @Override
  default boolean isProvided() {
    return true;
  }

}
