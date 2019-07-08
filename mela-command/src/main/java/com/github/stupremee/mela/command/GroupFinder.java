package com.github.stupremee.mela.command;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
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
    String directChild = input.substring(0, spaceIndex);
    input = input.substring(0, spaceIndex).trim();
    current.getChildren().stream()
        .filter((group) -> group.getAliases().contains(directChild))
        .findFirst()
        .ifPresentOrElse((group) -> {
          current = group;
          find();
        }, () -> result = current);
  }

  public CommandGroup getResult() {
    checkResultPresence();
    return result;
  }

  public String getRemainingInput() {
    checkResultPresence();
    return input;
  }

  private void checkResultPresence() {
    checkState(result != null, "find() was not called");
  }
}
