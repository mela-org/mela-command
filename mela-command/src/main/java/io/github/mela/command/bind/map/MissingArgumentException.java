package io.github.mela.command.bind.map;

import java.util.NoSuchElementException;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MissingArgumentException extends NoSuchElementException {

  public MissingArgumentException() {
    super();
  }

  public MissingArgumentException(String message) {
    super(message);
  }
}
