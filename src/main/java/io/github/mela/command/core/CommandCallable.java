package io.github.mela.command.core;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface CommandCallable {

  void call(@Nonnull Arguments arguments, @Nonnull CommandContext context);

  @Nonnull
  Set<String> getLabels();

  @Nullable
  default String getPrimaryLabel() {
    Set<String> labels = getLabels();
    return labels.isEmpty() ? null : labels.iterator().next();
  }

  @Nullable
  String getDescription();

  @Nullable
  String getHelp();

  @Nullable
  String getUsage();

}
