package io.github.mela.command.map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class ObjectWrapping {

  private final Object content;

  ObjectWrapping(Object content) {
    this.content = content;
  }

  Object getContent() {
    return content;
  }
}
