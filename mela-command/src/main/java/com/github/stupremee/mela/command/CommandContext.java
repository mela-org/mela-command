package com.github.stupremee.mela.command;

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

  public void put(Object key, Object value) {
    this.map.put(key, value);
  }

  public Optional<Object> get(Object key) {
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

  public static CommandContext create() {
    return new CommandContext();
  }

  public static CommandContext of(Map<?, ?> map) {
    return new CommandContext(new HashMap<>(map));
  }
}
