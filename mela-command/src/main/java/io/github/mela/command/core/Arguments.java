package io.github.mela.command.core;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.*;
import static java.lang.Character.isWhitespace;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Arguments {

  private final String raw;
  private final StringBuilder arguments;

  private int position;
  private char previous;

  private Arguments(String arguments) {
    this.raw = arguments.trim();
    this.arguments = new StringBuilder(raw);
    this.position = 0;
    this.previous = 0;
  }

  public static Arguments of(@Nonnull String arguments) {
    return new Arguments(checkNotNull(arguments));
  }

  public int indexOfWord(String word) {
    int startIndex = indexOf(word);
    int endIndex = startIndex + word.length();
    return startIndex != -1
        &&(startIndex == 0 || isWhitespace(charAt(startIndex - 1)))
        && (endIndex == length() || isWhitespace(charAt(endIndex + 1)))
        ? startIndex : -1;
  }

  public int indexOf(String substring) {
    return arguments.indexOf(substring);
  }

  public char charAt(int position) {
    return arguments.charAt(position);
  }

  public String nextScope(char scopeBegin, char scopeEnd) {
    checkArgument(isNextUnescaped(scopeBegin),
        "The next character must indicate a scope begin to parse the next scope");
    next();
    StringBuilder builder = new StringBuilder();
    int openScopes = 1;
    while (hasNext()) {
      if (isNextUnescaped(scopeBegin)) {
        ++openScopes;
      } else if (isNextUnescaped(scopeEnd) && --openScopes == 0) {
        next();
        break;
      }
      builder.append(next());
    }
    skipLeadingWhitespace();
    return builder.toString().trim();
  }

  public String nextString() {
    String string = isNextUnescaped('"') ? nextStringWithQuotationMarks() : nextWord();
    skipLeadingWhitespace();
    return string.trim();
  }

  private String nextWord() {
    StringBuilder builder = new StringBuilder();
    while (hasNext() && !isNextWhiteSpace() && !isNextUnescaped('"')) {
      builder.append(next());
    }
    return builder.toString();
  }

  private String nextStringWithQuotationMarks() {
    next();
    StringBuilder builder = new StringBuilder();
    while (hasNext()) {
      if (isNextUnescaped('"')) {
        next();
        break;
      } else {
        builder.append(next());
      }
    }
    return builder.toString();
  }

  public void skipLeadingWhitespace() {
    while (hasNext() && isNextWhiteSpace()) {
      next();
    }
  }

  private boolean isNextUnescaped(char c) {
    return peek() == c && previous != '\\';
  }

  private boolean isNextWhiteSpace() {
    return isWhitespace(peek());
  }

  public boolean hasNext() {
    return position < arguments.length();
  }

  public char next() {
    char next;
    try {
      next = charAt(position);
    } catch (IndexOutOfBoundsException e) {
      throw new ArgumentException("Reached end of arguments while trying to consume the next character", e);
    }
    previous = next;
    arguments.deleteCharAt(position);
    return next;
  }

  public char peek() {
    try {
      return peek(0);
    } catch (IndexOutOfBoundsException e) {
      throw new ArgumentException("Reached end of arguments while trying to peek the next character", e);
    }
  }

  public char peek(int delta) {
    return charAt(position + delta);
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
    this.position = checkPositionIndex(position, arguments.length());
  }

  public int length() {
    return arguments.length();
  }

  @Override
  public String toString() {
    return arguments.toString();
  }

  @Nonnull
  public String getRaw() {
    return raw;
  }
}
