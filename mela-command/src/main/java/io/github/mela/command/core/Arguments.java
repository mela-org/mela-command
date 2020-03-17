package io.github.mela.command.core;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO better exceptions for indexoutofbounds
public final class Arguments {

  private final StringBuilder arguments;

  private int position;
  private char previous;

  private Arguments(String arguments) {
    this.arguments = new StringBuilder(arguments.trim());
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
    return Character.isWhitespace(peek());
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

  public char peek() {
    return peek(0);
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
    this.position = position;
  }

  @Override
  public String toString() {
    return arguments.toString();
  }
}
