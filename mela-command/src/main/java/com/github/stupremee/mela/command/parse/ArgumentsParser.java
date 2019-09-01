package com.github.stupremee.mela.command.parse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class ArgumentsParser {

  private final String raw;
  private final List<String> arguments;
  private final Map<String, String> flags;

  private int position;

  ArgumentsParser(String rawArgs) {
    checkArgument(!rawArgs.isEmpty(), "Arguments string must not be empty");
    this.raw = rawArgs;
    this.position = -1;
    this.arguments = new ArrayList<>();
    this.flags = new HashMap<>();
  }

  Arguments parse() {
    while (!endOfString()) {
      if (Character.isWhitespace(peek())) {
        advance();
      } else if (validFlagBegin()) {
        advance();
        flag();
      } else {
        argument();
      }
    }
    return new Arguments(raw, arguments, flags);
  }

  private boolean validFlagBegin() {
    return peek() == '-' && !endOfString(2) && !Character.isDigit(peek(2));
  }

  private void flag() {
    String flag = nextString();
    while (!endOfString() && Character.isWhitespace(peek())) {
      advance();
    }
    String value = !endOfString() && !validFlagBegin() ? nextString() : "true";
    flags.put(flag, value);
  }

  private void argument() {
    arguments.add(nextString());
  }

  private String nextString() {
    if (endOfString())
      return "";

    StringBuilder builder = new StringBuilder();
    BooleanSupplier endCondition = peek() == '\"'
        ? () -> peek() == '\"'
        : () -> Character.isWhitespace(peek());
    while (!endOfString() && !endCondition.getAsBoolean()) {
      builder.append(advance());
    }
    return builder.toString();
  }

  private char advance() {
    return raw.charAt(++position);
  }

  private char peek() {
    return peek(1);
  }

  private char peek(int offset) {
    return raw.charAt(position + offset);
  }

  private boolean endOfString() {
    return endOfString(1);
  }

  private boolean endOfString(int offset) {
    return position + offset >= raw.length();
  }

}
