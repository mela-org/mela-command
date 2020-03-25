package io.github.mela.command.example.core;

import com.google.common.collect.ImmutableMap;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandCallableAdapter;
import io.github.mela.command.core.CommandContext;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class LeetifyCommand extends CommandCallableAdapter {

  private static final Map<Character, Character> REPLACEMENTS =
      ImmutableMap.of('e', '3', 'a', '4', 's', '5', 'l', '1', 'o', '0');

  public LeetifyCommand() {
    super(
        Arrays.asList("leetify", "leet"),
        "Leetifies the given text, e.g. \"hello world\" -> \"h3110 w0r1d\"",
        "Type \"fun leetify\" and append your piece of text!",
        "leetify/leet <text>"
    );
  }

  @Override
  public void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context) {
    while (arguments.hasNext()) {
      char next = arguments.next();
      System.out.print(REPLACEMENTS.getOrDefault(Character.toLowerCase(next), next));
    }
    System.out.println();
  }
}
