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
public final class CommandContext {

  private final Map<Object, Object> map;

  private CommandContext() {
    this(new HashMap<>());
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

  public <T> void put(Class<T> type, String id, T value) {
    map.put(new ContextKey(type, id), value);
  }

  @SuppressWarnings("unchecked")
  public <T> Optional<T> get(Class<T> type, String id) {
    return (Optional<T>) Optional.ofNullable(map.get(new ContextKey(type, id)));
  }

  public <T> void put(TypeLiteral<T> type, String id, T value) {
    map.put(new ContextKey(type.getType(), id), value);
  }

  @SuppressWarnings("unchecked")
  public <T> Optional<T> get(TypeLiteral<T> type, String id) {
    return (Optional<T>) Optional.ofNullable(map.get(new ContextKey(type.getType(), id)));
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

  private static final class ContextKey {
    final Type type;
    final String id;

    ContextKey(Type type, String id) {
      this.type = type;
      this.id = id;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ContextKey that = (ContextKey) o;
      return Objects.equals(type, that.type) &&
          Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
      return Objects.hash(type, id);
    }
  }

  @Nonnull
  public static CommandContext of(@Nonnull Map<?, ?> map) {
    return new CommandContext(new HashMap<>(checkNotNull(map)));
  }
}
