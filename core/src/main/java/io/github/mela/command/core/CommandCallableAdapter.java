package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkArgument;


import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class CommandCallableAdapter implements CommandCallable {

  private final Set<String> labels;
  private final String primaryLabel;
  private final String description;
  private final String help;
  private final String usage;

  protected CommandCallableAdapter(@Nonnull Set<String> labels,
                                   @Nullable String description,
                                   @Nullable String help,
                                   @Nullable String usage) {
    this(labels, null, description, help, usage);
  }

  protected CommandCallableAdapter(@Nonnull Set<String> labels,
                                   @Nullable String primaryLabel,
                                   @Nullable String description,
                                   @Nullable String help,
                                   @Nullable String usage) {
    this.labels = ImmutableSet.copyOf(labels);
    if (primaryLabel != null) {
      checkArgument(labels.contains(primaryLabel),
          "Primary label \"" + primaryLabel + "\" is not part of the command's labels"
              + " (" + labels + ")");
    }
    this.primaryLabel = primaryLabel;
    this.description = description;
    this.help = help;
    this.usage = usage;
  }

  @Nonnull
  @Override
  public final Set<String> getLabels() {
    return labels;
  }

  @Override
  public final String getPrimaryLabel() {
    return primaryLabel == null
        ? (labels.isEmpty() ? null : labels.iterator().next())
        : primaryLabel;
  }

  @Override
  public final String getDescription() {
    return description;
  }

  @Override
  public final String getHelp() {
    return help;
  }

  @Override
  public final String getUsage() {
    return usage;
  }

  @Override
  public String toString() {
    return "CommandCallable(" + labels + ")";
  }
}
