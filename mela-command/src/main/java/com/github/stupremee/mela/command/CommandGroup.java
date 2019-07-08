package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.inject.RootGroupProvider;
import com.google.inject.ProvidedBy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

@ProvidedBy(RootGroupProvider.class)
public interface CommandGroup {

  @Nullable
  CommandGroup getParent();

  @Nonnull
  Set<CommandGroup> getChildren();

  @Nonnull
  Set<CommandCallable> getCommands();

  @Nullable
  default String getPrimaryAlias() {
    Set<String> aliases = getAliases();
    return aliases.isEmpty() ? null : aliases.iterator().next();
  }

  @Nonnull
  Set<String> getAliases();

  @Nonnull
  default Optional<CommandGroup> findChild(String input) {
    final String childDescriptor = input.trim();
    int spaceIndex = childDescriptor.indexOf(' ');
    String directChildAlias = childDescriptor.substring(0, spaceIndex);
    return getChildren().stream()
        .filter((group) -> group.getAliases().contains(directChildAlias))
        .findFirst()
        .flatMap((group) -> group.findChild(childDescriptor.substring(spaceIndex)));
  }

}
