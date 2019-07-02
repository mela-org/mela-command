package com.github.stupremee.mela.command.map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class GenericWrapping<T> {

  private final T content;

  public GenericWrapping(T content) {
    this.content = content;
  }

  public T getContent() {
    return content;
  }
}
