package com.github.stupremee.mela.command;

import javax.annotation.Nonnull;
import java.util.Set;

public interface CommandCallable {

  void call(@Nonnull String arguments, @Nonnull CommandContext context);

  @Nonnull
  Set<String> getLabels();

  default String getPrimaryLabel() {
    Set<String> labels = getLabels();
    return labels.isEmpty() ? null : labels.iterator().next();
  }

  String getHelp();

  String getDescription();

  String getUsage();

}
