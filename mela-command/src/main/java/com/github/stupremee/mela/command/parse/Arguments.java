package com.github.stupremee.mela.command.parse;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Arguments {

  public static final Arguments EMPTY = new Arguments("", List.of(), Map.of());

  private final String raw;
  private final List<String> arguments;
  private final Map<String, String> flags;

  Arguments(String raw, List<String> arguments, Map<String, String> flags) {
    this.raw = raw;
    this.arguments = List.copyOf(arguments);
    this.flags = Map.copyOf(flags);
  }

  public String getRaw() {
    return raw;
  }

  public List<String> getArguments() {
	return arguments;
  }
  
  public Map<String, String> getFlags() {
    return flags;
  }
  
  public void forEachArgument(Consumer<String> action) {
    arguments.forEach(action);
  }

  public boolean hasFlag(String flag) {
    return flags.containsKey(flag);
  }

  public Optional<String> getFlagValue(String flag) {
     return Optional.ofNullable(flags.get(flag));
  }
  
  public static Arguments parse(String input) {
    return new ArgumentsParser(input).parse();
  }
}
