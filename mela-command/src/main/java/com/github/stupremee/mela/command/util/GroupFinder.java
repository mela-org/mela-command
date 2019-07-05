package com.github.stupremee.mela.command.util;

import com.github.stupremee.mela.command.CommandGroup;

import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class GroupFinder {

  private GroupFinder() {}

  public static Optional<CommandGroup> find(String childDescriptor, CommandGroup root) {
    return Optional.empty();
  }

  public static Optional<CommandGroup> findExact(String childDescriptor, CommandGroup root) {
    return Optional.empty();
  }

}
