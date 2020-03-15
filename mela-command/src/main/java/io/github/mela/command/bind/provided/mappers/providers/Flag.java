package io.github.mela.command.bind.provided.mappers.providers;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Flag<T> {

  private final T value;

  public Flag(T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }
}
