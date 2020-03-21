package io.github.mela.command.core;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandContext {

  private final Map<Object, Object> map;

  private CommandContext() {
    this(Maps.newHashMap());
  }

  private CommandContext(Map<Object, Object> map) {
    this.map = map;
  }

  public void put(@Nullable Object key, @Nullable Object value) {
    this.map.put(key, value);
  }

  @Nonnull
  public Optional<Object> get(@Nullable Object key) {
    return Optional.ofNullable(map.get(key));
  }

  public <T> void put(@Nonnull Class<T> type, @Nullable Object key, @Nullable T value) {
    put(new CompositeKey(type, key), value);
  }

  @SuppressWarnings("unchecked")
  @Nonnull
  public <T> Optional<T> get(@Nonnull Class<T> type, @Nullable Object key) {
    return (Optional<T>) get((Type) type, key);
  }

  @SuppressWarnings("UnstableApiUsage")
  public <T> void put(@Nonnull TypeToken<T> type, @Nullable Object key, @Nullable T value) {
    put(new CompositeKey(type.getType(), key), value);
  }

  @SuppressWarnings({"unchecked", "UnstableApiUsage"})
  @Nonnull
  public <T> Optional<T> get(@Nonnull TypeToken<T> type, @Nullable Object key) {
    return (Optional<T>) get(type.getType(), key);
  }

  @Nonnull
  public Optional<?> get(@Nonnull Type type, @Nullable Object key) {
    return Optional.ofNullable(map.get(new CompositeKey(type, key)));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    CommandContext that = (CommandContext) o;
    return Objects.equals(map, that.map);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(map);
  }

  @Nonnull
  public static CommandContext create() {
    return new CommandContext();
  }

  private static final class CompositeKey {
    final Type type;
    final Object key;

    CompositeKey(Type type, Object key) {
      this.type = checkNotNull(type);
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
  public static CommandContext of(@Nonnull Map<?, ?> map) {
    return new CommandContext(Maps.newHashMap(map));
  }
}
