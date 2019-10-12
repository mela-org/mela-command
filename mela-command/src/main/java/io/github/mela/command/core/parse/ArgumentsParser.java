package io.github.mela.command.core.parse;

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

  private final String input;
  private final StringBuilder rawBuilder;
  private final List<String> arguments;
  private final Map<String, String> flags;

  private boolean noMoreFlags;
  private int position;

  ArgumentsParser(String input) {
    checkArgument(!input.isEmpty(), "Arguments string must not be empty");
    this.input = input;
    this.rawBuilder = new StringBuilder();
    this.position = -1;
    this.noMoreFlags = false;
    this.arguments = new ArrayList<>();
    this.flags = new HashMap<>();
  }

  Arguments parse() {
    while (!endOfString()) {
      if (Character.isWhitespace(peek())) {
        if (noMoreFlags) {
          rawBuilder.append(advance());
        } else {
          advance();
        }
      } else if (validFlagBegin()) {
        if (noMoreFlags) {
          rawBuilder.append(argument());
        } else {
          advance();
          flag();
        }
      } else {
        noMoreFlags = true;
        rawBuilder.append(argument());
      }
    }
    return new Arguments(rawBuilder.toString().trim(), arguments, flags);
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

  private String argument() {
    String argument = nextString();
    arguments.add(argument);
    return argument;
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
    return input.charAt(++position);
  }

  private char peek() {
    return peek(1);
  }

  private char peek(int offset) {
    return input.charAt(position + offset);
  }

  private boolean endOfString() {
    return endOfString(1);
  }

  private boolean endOfString(int offset) {
    return position + offset >= input.length();
  }

}
