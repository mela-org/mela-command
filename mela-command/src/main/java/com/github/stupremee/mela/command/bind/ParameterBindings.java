package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Key;

import java.util.Optional;

public interface ParameterBindings {

  <T> Optional<ArgumentMapper<T>> getMapper(Key<T> key);

}
