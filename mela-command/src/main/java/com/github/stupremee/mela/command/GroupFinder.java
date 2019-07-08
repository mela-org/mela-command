package com.github.stupremee.mela.command;

import javax.annotation.Nonnull;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 08.07.2019 improve this
public final class GroupFinder {

  private final boolean strict;

  private CommandGroup current;
  private String input;

  private CommandGroup result;

  public static GroupFinder strict(CommandGroup root, String input) {
    return new GroupFinder(true, root, input);
  }

  public static GroupFinder flexible(CommandGroup root, String input) {
    return new GroupFinder(false, root, input);
  }

  private GroupFinder(boolean strict, CommandGroup root, String input) {
    this.strict = strict;
    this.current = root;
    this.input = input;
  }

  public void find() {
    int spaceIndex = input.indexOf(' ');
    String directChild = input.substring(0, spaceIndex == -1 ? input.length() : spaceIndex);
    input = spaceIndex == -1 ? "" : input.substring(spaceIndex).trim();
    current.getChildren().stream()
        .filter((group) -> group.getNames().contains(directChild))
        .findFirst()
        .ifPresentOrElse((group) -> {
          current = group;
          find();
        }, () -> result = strict && !input.isEmpty() ? null : current);
  }

  @Nonnull
  public Optional<CommandGroup> getResult() {
    checkResultPresence();
    return Optional.ofNullable(result);
  }

  public String getRemainingInput() {
    checkResultPresence();
    return input;
  }

  private void checkResultPresence() {
    checkState(result != null, "find() was not called");
  }
}
