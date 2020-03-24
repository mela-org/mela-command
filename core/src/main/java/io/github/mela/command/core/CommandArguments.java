package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndex;
import static java.lang.Character.isWhitespace;


import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandArguments {

  private final String raw;
  private final StringBuilder arguments;

  private int position;
  private char previous;

  private char stringDelimiter;

  protected CommandArguments(String arguments) {
    this.raw = arguments.trim();
    this.arguments = new StringBuilder(raw);
    this.position = 0;
    this.previous = 0;
    this.stringDelimiter = '"';
  }

  public static CommandArguments of(@Nonnull String arguments) {
    return new CommandArguments(checkNotNull(arguments));
  }

  public int indexOfWord(String word) {
    int startIndex = indexOf(word);
    int endIndex = startIndex + word.length();
    return startIndex != -1
        && (startIndex == 0 || isWhitespace(charAt(startIndex - 1)))
        && (endIndex == length() || isWhitespace(charAt(endIndex)))
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
    String string = isNextUnescaped(stringDelimiter) ? nextSection(stringDelimiter) : nextWord();
    skipLeadingWhitespace();
    return string.trim();
  }

  public String nextWord() {
    StringBuilder builder = new StringBuilder();
    while (hasNext() && !isNextWhiteSpace()) {
      builder.append(next());
    }
    return builder.toString();
  }

  public String nextSection(char delimiter) {
    next();
    StringBuilder builder = new StringBuilder();
    while (hasNext()) {
      if (isNextUnescaped(delimiter)) {
        next();
        break;
      } else if (peek() == stringDelimiter && previous == '\\') {
        builder.deleteCharAt(builder.length() - 1);
      }
      builder.append(next());
    }
    return builder.toString();
  }

  public void skipLeadingWhitespace() {
    while (hasNext() && isNextWhiteSpace()) {
      next();
    }
  }

  public boolean isNextUnescaped(char c) {
    return peek() == c && previous != '\\';
  }

  // TODO consider using different whitespace specification,
  //  such as guava CharMatcher.breakingWhitespace()
  public boolean isNextWhiteSpace() {
    return isWhitespace(peek());
  }

  public String remaining() {
    StringBuilder builder = new StringBuilder();
    while (hasNext()) {
      builder.append(next());
    }
    return builder.toString().trim();
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

  public void setPosition(int position) {
    this.position = checkPositionIndex(position, arguments.length());
  }

  public char getStringDelimiter() {
    return stringDelimiter;
  }

  public void setStringDelimiter(char stringDelimiter) {
    this.stringDelimiter = stringDelimiter;
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
