package io.github.mela.command.core;

import javax.annotation.Nonnull;
import java.util.function.IntPredicate;

import static com.google.common.base.Preconditions.*;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Arguments {

  private final StringBuilder arguments;

  private int position;
  private char previous;

  private Arguments(String arguments) {
    this.arguments = new StringBuilder(arguments);
    this.position = 0;
    this.previous = 0;
  }

  public static Arguments of(@Nonnull String arguments) {
    return new Arguments(checkNotNull(arguments));
  }

  public int indexOf(String substring) {
    return arguments.indexOf(substring);
  }

  public char charAt(int position) {
    return arguments.charAt(position);
  }

  public String nextWord() {
    return nextUntil(Character::isWhitespace);
  }

  public String nextUntil(char character) {
    return nextUntil((c) -> c == character && previous != '\\');
  }

  public String nextScope(char scopeBegin, char scopeEnd) {
    return nextUntil(new IntPredicate() {
      int openScopes = 0;

      @Override
      public boolean test(int value) {
        if (previous != '\\') {
          if (value == scopeBegin) {
            ++openScopes;
          } else if (value == scopeEnd) {
            return --openScopes == 0;
          }
        }
        return false;
      }
    });
  }

  public String nextUntil(IntPredicate predicate) {
    StringBuilder builder = new StringBuilder();
    while (hasNext()) {
      char next = next();
      if (predicate.test(next)) {
        break;
      } else {
        builder.append(next);
      }
    }
    return builder.toString();
  }

  public boolean hasNext() {
    return position < arguments.length();
  }

  public char next() {
    char next = charAt(position);
    previous = next;
    arguments.deleteCharAt(position);
    return next;
  }

  public char peekNext() {
    return charAt(position);
  }

  public char previous() {
    return previous;
  }

  public void resetPosition() {
    position = 0;
  }

  public int position() {
    return position;
  }

  public void jumpTo(int position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return arguments.toString();
  }
}
