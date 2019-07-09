package com.github.stupremee.mela.command;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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

  @Nonnull
  public static CommandContext of(@Nonnull Map<?, ?> map) {
    return new CommandContext(new HashMap<>(map));
  }
}
