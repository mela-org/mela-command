package io.github.mela.command.core;

import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;

public interface CommandCallable {

  void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context);

  @Nonnull
  Set<String> getLabels();

  @Nonnull
  default Optional<String> getPrimaryLabel() {
    Set<String> labels = getLabels();
    return labels.isEmpty()
        ? Optional.empty()
        : Optional.of(labels.iterator().next());
  }

  @Nonnull
  Optional<String> getDescription();

  @Nonnull
  Optional<String> getHelp();

  @Nonnull
  Optional<String> getUsage();

}
