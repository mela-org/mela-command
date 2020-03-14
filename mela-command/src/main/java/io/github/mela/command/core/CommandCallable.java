package io.github.mela.command.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public interface CommandCallable {

  void call(@Nonnull String arguments, @Nonnull ContextMap context);

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
