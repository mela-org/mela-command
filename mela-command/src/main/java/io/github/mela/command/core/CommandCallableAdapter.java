package io.github.mela.command.core;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class CommandCallableAdapter implements CommandCallable {

  private final Set<String> labels;
  private final String primaryLabel;
  private final String help;
  private final String description;
  private final String usage;

  protected CommandCallableAdapter(@Nonnull Set<String> labels, String help, String description, String usage) {
    this(labels, null, help, description, usage);
  }

  protected CommandCallableAdapter(@Nonnull Set<String> labels,
                                   String primaryLabel, String help,
                                   String description, String usage) {
    this.labels = Set.copyOf(labels);
    this.primaryLabel = primaryLabel;
    this.help = help;
    this.description = description;
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
  public String getUsage() {
    return usage;
  }
}
