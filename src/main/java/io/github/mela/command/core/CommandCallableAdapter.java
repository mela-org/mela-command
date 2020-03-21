package io.github.mela.command.core;

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
    this.labels = Set.copyOf(labels);
    this.primaryLabel = primaryLabel;
    this.description = description;
    this.help = help;
    this.usage = usage;
  }

  @Nonnull
  @Override
  public Set<String> getLabels() {
    return labels;
  }

  @Override
  public String getPrimaryLabel() {
    return primaryLabel == null
        ? (labels.isEmpty() ? null : labels.iterator().next())
        : primaryLabel;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getHelp() {
    return help;
  }

  @Override
  public String getUsage() {
    return usage;
  }
}
