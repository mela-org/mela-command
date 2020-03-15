package io.github.mela.command.core;

import javax.annotation.Nonnull;
import java.util.function.IntPredicate;

import static com.google.common.base.Preconditions.*;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Arguments {

  private int position;
  private String arguments;

  private Arguments(String arguments) {
    this.arguments = arguments;
    this.position = 0;
  }

  public static Arguments of(@Nonnull String arguments) {
    return new Arguments(checkNotNull(arguments));
  }

  public String nextWord() {
    return nextUntil(Character::isWhitespace);
  }

  public String nextUntil(char character) {
    return nextUntil((c) -> c == character && peekPrevious() != '\\');
  }

  public String nextScope(char scopeBegin, char scopeEnd) {
    return nextUntil(new IntPredicate() {
      int openScopes = 0;
      @Override
      public boolean test(int value) {
        if (value == scopeBegin) {
          ++openScopes;
        } else if (value == scopeEnd) {
          return --openScopes == 0;
        }
        return false;
      }
    });
  }

  public String nextUntil(IntPredicate predicate) {
    int start = position;
    while (hasNext()) {
      char next = peekNext();
      if (predicate.test(next)) {
        break;
      }
      next();
    }
    int end = position;
    return arguments.substring(start, end);
  }

  public boolean hasNext() {
    return position < arguments.length();
  }

  public char next() {
    return arguments.charAt(position++);
  }

  public char previous() {
    return arguments.charAt(--position);
  }

  public char peekNext() {
    return peek(0);
  }

  public char peekPrevious() {
    return peek(- 1);
  }

  public char peek(int delta) {
    return arguments.charAt(position + delta);
  }

  public void remove(int from, int to) {
    checkArgument(from <= to,
        "The starting index of the section to remove must not be greater than the ending index ");
    arguments = arguments.substring(0, from) + arguments.substring(to);
    if (position >= to) {
      position -= to - from;
    } else if (position >= from) {
      position = from - 1;
    }
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return arguments;
  }
}
