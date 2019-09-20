package com.github.stupremee.mela.command.parse;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.CommandGroup;

import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class CommandInputParser {

  private String currentWord;
  private String remaining;

  private final String initialInput;
  private CommandGroup group;
  private CommandCallable callable;
  private Arguments arguments;

  CommandInputParser(CommandGroup root, String input) {
    this.initialInput = checkNotNull(input);
    this.remaining = input.trim();
    this.currentWord = "";
    this.group = checkNotNull(root);
  }

  public CommandInput parse() {
    stripGroup();
    stripCallable();
    parseRemaining();
    return new CommandInput(initialInput, group, callable, arguments);
  }

  private void parseRemaining() {
    arguments = Arguments.parse(remaining);
  }

  private void stripGroup() {
    while (!remaining.isEmpty()) {
      nextWord();
      CommandGroup child = findIn(group.getChildren(), (group) -> group.getNames().contains(currentWord));
      if (child == null)
        return;
      group = child;
    }
  }

  private void stripCallable() {
    nextWord();
    CommandCallable labelledCallable =
        findIn(group.getCommands(), (command) -> command.getLabels().contains(currentWord));
    callable = labelledCallable == null
        ? findIn(group.getCommands(), (command) -> command.getLabels().isEmpty())
        : labelledCallable;
  }

  private void nextWord() {
    remaining = remaining.substring(currentWord.length()).trim();
    int whitespaceIndex = nextWhitespace();
    currentWord = whitespaceIndex == -1 ? remaining : remaining.substring(0, whitespaceIndex);
  }

  private int nextWhitespace() {
    int space = remaining.indexOf(' ');
    if (space != -1)
      return space;
    int newLine = remaining.indexOf('\n');
    if (newLine != -1)
      return newLine;
    int tab = remaining.indexOf('\t');
    if (tab != -1)
      return tab;
    return -1;
  }

  private <T> T findIn(Iterable<T> iterable, Predicate<T> predicate) {
    for (T t : iterable) {
      if (predicate.test(t)) {
        return t;
      }
    }
    return null;
  }
}
