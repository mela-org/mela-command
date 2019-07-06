package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Key;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface ParameterBindings {

  @Nonnull
  <T> Optional<ArgumentMapper<T>> getMapper(Key<T> key);

}
