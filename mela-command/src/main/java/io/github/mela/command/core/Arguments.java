package io.github.mela.command.core;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
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

  public String nextString() {
    skipLeadingWhitespace();
    return isNextQuotationMark() ? nextStringQuotationMarks() : nextStringNormal();
  }

  private String nextStringNormal() {
    StringBuilder builder = new StringBuilder();
    while (hasNext()) {
      if (isNextWhiteSpace()) {
        break;
      }
      builder.append(next());
    }
    return builder.toString();
  }

  private String nextStringQuotationMarks() {
    StringBuilder builder = new StringBuilder();
    next();
    while (hasNext()) {
      if (isNextQuotationMark()) {
        next();
        if (isNextWhiteSpace()) {
          break;
        } else {
          builder.append('"');
          continue;
        }
      }
      builder.append(next());
    }
    return builder.toString();
  }

  private void skipLeadingWhitespace() {
    while (hasNext() && isNextWhiteSpace()) {
      next();
    }
  }

  private boolean isNextQuotationMark() {
    return peek() == '"' && previous != '\\';
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
