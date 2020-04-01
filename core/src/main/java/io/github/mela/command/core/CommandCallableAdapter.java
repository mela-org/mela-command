package io.github.mela.command.core;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class CommandCallableAdapter implements CommandCallable {

  private final Set<String> labels;
  private final Optional<String> primaryLabel;
  private final Optional<String> description;
  private final Optional<String> help;
  private final Optional<String> usage;

  protected CommandCallableAdapter(@Nonnull Iterable<String> labels,
                                   @Nullable String description,
                                   @Nullable String help,
                                   @Nullable String usage) {
    this.labels = ImmutableSet.copyOf(labels);
    this.primaryLabel = this.labels.isEmpty()
        ? Optional.empty()
        : Optional.of(labels.iterator().next());
    this.description = Optional.ofNullable(description);
    this.help = Optional.ofNullable(help);
    this.usage = Optional.ofNullable(usage);
  }

  @Nonnull
  @Override
  public final Set<String> getLabels() {
    return labels;
  }

  @Nonnull
  @Override
  public final Optional<String> getPrimaryLabel() {
    return primaryLabel;
  }

  @Nonnull
  @Override
  public final Optional<String> getDescription() {
    return description;
  }

  @Nonnull
  @Override
  public final Optional<String> getHelp() {
    return help;
  }

  @Nonnull
  @Override
  public final Optional<String> getUsage() {
    return usage;
  }

  @Override
  public String toString() {
    return "CommandCallable(" + labels + ")";
  }
}
