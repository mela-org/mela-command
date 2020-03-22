package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class CommandInputParser {

  private final String initialInput;
  private String currentWord;
  private String remaining;
  private CommandGroup group;
  private CommandCallable command;

  CommandInputParser(CommandGroup root, String input) {
    this.initialInput = checkNotNull(input);
    this.remaining = input.trim();
    this.currentWord = "";
    this.group = checkNotNull(root);
  }

  CommandInput parse() {
    stripGroup();
    stripCommand();
    return new CommandInput(initialInput, group, command, remaining);
  }

  private void stripGroup() {
    while (!remaining.isEmpty()) {
      nextWord();
      Optional<? extends CommandGroup> child = group.findChild(currentWord);
      if (!child.isPresent()) {
        return;
      }
      stripCurrentWord();
      group = child.get();
    }
  }

  @SuppressWarnings("unchecked")
  private void stripCommand() {
    command = ((Optional<CommandCallable>) group.findCommand(currentWord))
        .orElseGet(() -> group.findDefaultCommand().orElse(null));
    if (command != null) {
      stripCurrentWord();
    }
  }

  private void nextWord() {
    int whitespaceIndex = nextWhitespace();
    currentWord = whitespaceIndex == remaining.length()
        ? remaining
        : remaining.substring(0, whitespaceIndex);
  }

  private void stripCurrentWord() {
    remaining = remaining.substring(currentWord.length()).trim();
  }

  private int nextWhitespace() {
    int nextWhiteSpace;
    for (nextWhiteSpace = 0;
         nextWhiteSpace < remaining.length()
             && !Character.isWhitespace(remaining.charAt(nextWhiteSpace));
         ++nextWhiteSpace) {
      ;
    }
    return nextWhiteSpace;
  }
}
