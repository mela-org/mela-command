package io.github.mela.command.bind.map;

import java.util.Objects;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingContextKey {

  private final Object id;
  private final Object key;

  private MappingContextKey(Object id, Object key) {
    this.id = id;
    this.key = key;
  }

  public static MappingContextKey of(Object id, Object key) {
    return new MappingContextKey(id, key);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MappingContextKey that = (MappingContextKey) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(key, that.key);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key);
  }

  @Override
  public String toString() {
    return "MappingContextKey{" +
        "id=" + id +
        ", key=" + key +
        '}';
  }
}
