package com.github.stupremee.mela.command.core;

import com.github.stupremee.mela.command.core.parse.Arguments;

import javax.annotation.Nonnull;
import java.util.Set;

public interface CommandCallable {

  void call(@Nonnull Arguments arguments, @Nonnull CommandContext context);

  @Nonnull
  Set<String> getLabels();

  default String getPrimaryLabel() {
    Set<String> labels = getLabels();
    return labels.isEmpty() ? null : labels.iterator().next();
  }

  String getDescription();

  String getUsage();

}
