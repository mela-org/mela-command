package com.github.stupremee.mela.command;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 08.07.2019 improve this
public final class GroupFinder {

  private CommandGroup current;
  private String input;

  private CommandGroup result;

  public static GroupFinder of(CommandGroup root, String input) {
    return new GroupFinder(root, input);
  }

  private GroupFinder(CommandGroup root, String input) {
    this.current = root;
    this.input = input;
  }

  public void find() {
    int spaceIndex = input.indexOf(' ');
    boolean lastWord = spaceIndex == -1;
    String directChild = input.substring(0, lastWord ? input.length() : spaceIndex);
    input = lastWord ? "" : input.substring(spaceIndex).trim();
    current.getChildren().stream()
        .filter((group) -> group.getNames().contains(directChild))
        .findFirst()
        .ifPresentOrElse((group) -> {
          current = group;
          find();
        }, () -> result = current);
  }

  public void findExact() {
    find();
    if (hasRemainingInput())
      result = null;
  }

  @Nonnull
  public CommandGroup getResult() {
    checkResultPresence();
    return result;
  }

  public boolean hasRemainingInput() {
    checkResultPresence();
    return !input.isEmpty();
  }

  public String getRemainingInput() {
    checkResultPresence();
    return input;
  }

  private void checkResultPresence() {
    checkState(result != null, "No group has been found");
  }
}
