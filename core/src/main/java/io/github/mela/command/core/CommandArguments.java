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

  public static final char DEFAULT_STRING_DELIMITER = '"';

  private final String raw;
  private final StringBuilder arguments;
  private final char stringDelimiter;

  private int rawCursor;
  private int position;
  private char previous;

  protected CommandArguments(@Nonnull String arguments, char stringDelimiter) {
    this.raw = arguments.trim();
    this.arguments = new StringBuilder(raw);
    this.rawCursor = 0;
    this.position = 0;
    this.previous = 0;
    this.stringDelimiter = stringDelimiter;
  }

  @Nonnull
  public static CommandArguments of(@Nonnull String arguments) {
    return of(arguments, DEFAULT_STRING_DELIMITER);
  }

  @Nonnull
  public static CommandArguments of(@Nonnull String arguments, char stringDelimiter) {
    return new CommandArguments(checkNotNull(arguments), stringDelimiter);
  }

  @Nonnull
  public static CommandArguments singleString(@Nonnull String string) {
    return singleString(string, DEFAULT_STRING_DELIMITER);
  }

  @Nonnull
  public static CommandArguments singleString(@Nonnull String string, char stringDelimiter) {
    checkNotNull(string);
    StringBuilder escaped = new StringBuilder();
    escaped.append(stringDelimiter);
    for (char c : string.toCharArray()) {
      if (c == stringDelimiter) {
        escaped.append('\\');
      }
      escaped.append(c);
    }
    escaped.append(stringDelimiter);
    return of(escaped.toString(), stringDelimiter);
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
      if (isNextUnescaped(scopeEnd) && --openScopes == 0) {
        next();
        break;
      } else if (isNextUnescaped(scopeBegin)) {
        ++openScopes;
      }
      builder.append(next());
    }
    skipLeadingWhitespace();
    return builder.toString().trim();
  }

  public String nextString() {
    return isNextUnescaped(stringDelimiter) ? nextSection(stringDelimiter) : nextWord();
  }

  public String nextWord() {
    StringBuilder builder = new StringBuilder();
    while (hasNext() && !isNextWhiteSpace()) {
      builder.append(next());
    }
    skipLeadingWhitespace();
    return builder.toString();
  }

  // TODO rename to nextUntil - remove argument check
  public String nextSection(char delimiter) {
    checkArgument(isNextUnescaped(delimiter),
        "The next character must indicate a section begin to parse the next section");
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
    skipLeadingWhitespace();
    return builder.toString().trim();
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
      throw ArgumentException.create(
          "Missing argument: Reached end of arguments while parsing", this);
    }
    previous = next;
    arguments.deleteCharAt(position);
    ++rawCursor;
    return next;
  }

  public char peek() {
    try {
      return peek(0);
    } catch (IndexOutOfBoundsException e) {
      throw ArgumentException.create(
          "Missing argument: Reached end of arguments while parsing", this);
    }
  }

  public char peek(int delta) {
    return charAt(position + delta);
  }

  public char previous() {
    return previous;
  }

  public void resetPosition() {
    setPosition(0);
  }

  public int position() {
    return position;
  }

  public void setPosition(int position) {
    checkPositionIndex(position, arguments.length());
    rawCursor -= this.position - position;
    this.position = position;
  }

  public int getRawCursor() {
    return rawCursor;
  }

  public char getStringDelimiter() {
    return stringDelimiter;
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
