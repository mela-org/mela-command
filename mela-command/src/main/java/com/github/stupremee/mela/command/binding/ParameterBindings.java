package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.inject.Key;

import java.util.Optional;

public interface ParameterBindings {

  <T> Optional<ArgumentMapper<T>> getMapper(Key<T> key);

}
