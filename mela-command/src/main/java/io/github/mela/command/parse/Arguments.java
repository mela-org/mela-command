package io.github.mela.command.parse;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Arguments {

  public static final Pattern PATTERN = Pattern.compile("\\s*(-[^\\d]+(\\s+(\".+\"|[^\\s\"]+))?)*(\\s+(\".+\"|[^\"\\s]+))*\\s*");
  public static final Arguments EMPTY = new Arguments("", List.of(), Map.of());

  private final String rawArgs;
  private final List<String> arguments;
  private final Map<String, String> flags;

  Arguments(String rawArgs, List<String> arguments, Map<String, String> flags) {
    this.rawArgs = rawArgs;
    this.arguments = List.copyOf(arguments);
    this.flags = Map.copyOf(flags);
  }

  public String getRawArgs() {
    return rawArgs;
  }

  public String get(int index) {
    return arguments.get(index);
  }

  public int size() {
    return arguments.size();
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

  public String getFlagValue(String flag) {
     return flags.get(flag);
  }
  
  public static Arguments parse(String input) {
    return new ArgumentsParser(input).parse();
  }
}
