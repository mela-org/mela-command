package io.github.mela.command;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class EmptyGroup implements CommandGroup {

  public static final EmptyGroup INSTANCE = new EmptyGroup();

  @Override
  public String toString() {
    return "[]";
  }

  @Nullable
  @Override
  public CommandGroup getParent() {
    return null;
  }

  @Nonnull
  @Override
  public Set<CommandGroup> getChildren() {
    return Collections.emptySet();
  }

  @Nonnull
  @Override
  public Set<String> getNames() {
    return Collections.emptySet();
  }

  @Nonnull
  @Override
  public Set<CommandCallable> getCommands() {
    return Collections.emptySet();
  }
}
