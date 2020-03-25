package io.github.mela.command.example.core;

import com.google.common.collect.ImmutableList;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandCallableAdapter;
import io.github.mela.command.core.CommandContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class RandomiseCommand extends CommandCallableAdapter {

  public RandomiseCommand() {
    super(
        ImmutableList.of("randomise", "rand"),
        "Randomises the characters in the middle of a word, e.g. \"hello world\" -> \"hlelo wlord\"",
        "Type \"fun randomise\" and append your piece of text!",
        "randomise/rand <text>"
    );
  }

  @Override
  public void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context) {
    StringBuilder output = new StringBuilder();
    while (arguments.hasNext()) {
      String word = arguments.nextWord();
      if (word.length() > 3) {
        output.append(word.charAt(0));
        List<Character> middle = word.chars()
            .skip(1)
            .limit(word.length() - 2)
            .mapToObj((i) -> (char) i)
            .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(middle);
        middle.forEach(output::append);
        output.append(word.charAt(word.length() - 1));
      } else {
        output.append(word);
      }
      output.append(' ');
    }
    System.out.println(output);
  }
}
