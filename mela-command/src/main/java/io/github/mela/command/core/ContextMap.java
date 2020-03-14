package io.github.mela.command.core;

import com.google.inject.TypeLiteral;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ContextMap {

  private final Map<Object, Object> map;

  private ContextMap() {
    this(new HashMap<>());
  }

  private ContextMap(Map<Object, Object> map) {
    this.map = map;
  }

  public void put(@Nullable Object key, @Nullable Object value) {
    this.map.put(key, value);
  }

  @Nonnull
  public Optional<Object> get(@Nullable Object key) {
    return Optional.ofNullable(map.get(key));
  }

  public <T> void put(Class<T> type, Object key, T value) {
    map.put(new CompositeKey(type, key), value);
  }

  @SuppressWarnings("unchecked")
  public <T> Optional<T> get(Class<T> type, Object key) {
    return (Optional<T>) get((Type) type, key);
  }

  public <T> void put(TypeLiteral<T> type, Object key, T value) {
    map.put(new CompositeKey(type.getType(), key), value);
  }

  @SuppressWarnings("unchecked")
  public <T> Optional<T> get(TypeLiteral<T> type, Object key) {
    return (Optional<T>) get(type.getType(), key);
  }

  public Optional<?> get(Type type, Object key) {
    return Optional.ofNullable(map.get(new CompositeKey(type, key)));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ContextMap that = (ContextMap) o;
    return Objects.equals(map, that.map);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(map);
  }

  @Nonnull
  public static ContextMap create() {
    return new ContextMap();
  }

  private static final class CompositeKey {
    final Type type;
    final Object key;

    CompositeKey(Type type, Object key) {
      this.type = type;
      this.key = key;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      CompositeKey that = (CompositeKey) o;
      return Objects.equals(type, that.type) &&
          Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, key);
    }
  }

  @Nonnull
  public static ContextMap of(@Nonnull Map<?, ?> map) {
    return new ContextMap(new HashMap<>(checkNotNull(map)));
  }
}
