package com.github.stupremee.mela.command.internal;

import com.github.stupremee.mela.command.binding.ParameterBindings;
import com.github.stupremee.mela.command.mapping.ArgumentMapper;
import com.google.common.collect.Maps;
import com.google.inject.Key;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InjectableParameterBindings implements ParameterBindings {

  private final Map<Key<?>, ValueWrapper<?>> bindings;

  InjectableParameterBindings() {
    this.bindings = Maps.newHashMap();
  }

  private InjectableParameterBindings(Map<Key<?>, ValueWrapper<?>> bindings) {
    this.bindings = Maps.newHashMap(bindings);
  }

  @SuppressWarnings("unchecked") // type is always correct (map is encapsulated)
  @Override
  public <T> Optional<ArgumentMapper<T>> getMapper(Key<T> key) {
    return Optional.ofNullable((ValueWrapper<T>) bindings.get(key))
        .map((value) -> value.mapper);
  }

  <T> void put(Key<T> key, Class<? extends ArgumentMapper<T>> mapperType) {
    bindings.put(key, new ValueWrapper<>(mapperType));
  }

  void putAll(InjectableParameterBindings bindings) {
    this.bindings.putAll(bindings.bindings);
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

  InjectableParameterBindings copy() {
    return new InjectableParameterBindings(bindings);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InjectableParameterBindings that = (InjectableParameterBindings) o;
    return Objects.equals(bindings, that.bindings);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(bindings);
  }

  private static final class ValueWrapper<T> {
    final Class<? extends ArgumentMapper<T>> mapperType;
    ArgumentMapper<T> mapper;

    ValueWrapper(Class<? extends ArgumentMapper<T>> mapperType) {
      this.mapperType = mapperType;
    }
  }


}
