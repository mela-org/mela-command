package com.github.stupremee.mela.command.util;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.bind.tree.CommandGroup;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandInputParser {

  private String currentWord;
  private String remaining;

  private CommandGroup group;
  private CommandCallable callable;
  private String arguments;

  public CommandInputParser(@Nonnull CommandGroup root, @Nonnull String input) {
    this.remaining = checkNotNull(input.trim());
    this.currentWord = "";
    this.group = checkNotNull(root);
  }

  public CommandInput parse() {
    stripGroup();
    stripCallable();
    return new CommandInput(group, callable, remaining);
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
    int spaceIndex = remaining.indexOf(' ');
    currentWord = spaceIndex == -1 ? remaining : remaining.substring(0, spaceIndex);
  }

  private <T> T findIn(Iterable<T> iterable, Predicate<T> predicate) {
    for (T t : iterable) {
      if (predicate.test(t)) {
        return t;
      }
    }
    return null;
  }

  @Nonnull
  public static CommandInput parse(@Nonnull CommandGroup root, @Nonnull String input) {
    return new CommandInputParser(checkNotNull(root), checkNotNull(input)).parse();
  }

}
