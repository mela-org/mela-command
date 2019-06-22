package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalParameterBindings implements ParameterBindings {

  private final Map<Key<?>, ValueWrapper<?>> bindings;

  InternalParameterBindings() {
    this.bindings = Maps.newHashMap();
  }

  private InternalParameterBindings(Map<Key<?>, ValueWrapper<?>> bindings) {
    this.bindings = Maps.newHashMap(bindings);
  }

  @SuppressWarnings("unchecked") // type is always correct (map is encapsulated)
  @Override
  public <T> Optional<ArgumentMapper<T>> getMapper(Key<T> key) {
    return Optional.ofNullable((ValueWrapper<T>) bindings.get(key))
        .map((value) -> value.mapper);
  }

  <T> void add(Key<T> key, Class<? extends ArgumentMapper<T>> mapperType) {
    bindings.put(key, new ValueWrapper<>(mapperType));
  }

  void inject(Set<ArgumentMapper<?>> mappers) {
    for (ValueWrapper value : bindings.values()) {
      for (ArgumentMapper mapper : mappers) {
        if (value.mapperType == mapper.getClass()) {
          value.mapper = mapper;
        }
      }
    }
  }

  InternalParameterBindings copy() {
    return new InternalParameterBindings(bindings);
  }

  private static final class ValueWrapper<T> {
    final Class<? extends ArgumentMapper<T>> mapperType;
    ArgumentMapper<T> mapper;

    ValueWrapper(Class<? extends ArgumentMapper<T>> mapperType) {
      this.mapperType = mapperType;
    }
  }


}
