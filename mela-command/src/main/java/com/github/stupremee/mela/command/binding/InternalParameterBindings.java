package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalParameterBindings implements ParameterBindings {

  private final Map<Key<?>, ArgumentMapper<?>> bindings = Maps.newHashMap();

  @SuppressWarnings("unchecked") // type is always correct (map is encapsulated)
  @Override
  public <T> Optional<ArgumentMapper<T>> getMapper(Key<T> key) {
    return Optional.ofNullable((ArgumentMapper<T>) bindings.get(key));
  }

  <T> void put(Key<T> key, ArgumentMapper<T> mapper) {
    bindings.put(key, mapper);
  }
}
