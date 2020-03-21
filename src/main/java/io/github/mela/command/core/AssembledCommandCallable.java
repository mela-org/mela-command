package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


import java.util.Set;
import java.util.function.BiConsumer;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class AssembledCommandCallable extends CommandCallableAdapter {

  private final BiConsumer<CommandArguments, CommandContext> action;

  private AssembledCommandCallable(
      @Nonnull Set<String> labels,
      @Nullable String primaryLabel,
      @Nullable String help,
      @Nullable String description,
      @Nullable String usage,
      @Nonnull BiConsumer<CommandArguments, CommandContext> action
  ) {
    super(labels, primaryLabel, description, help, usage);
    this.action = checkNotNull(action);
  }

  @Nonnull
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context) {
    action.accept(arguments, context);
  }

  public static final class Builder {

    private Set<String> labels = Set.of();
    private String primaryLabel = null;
    private String help = null;
    private String description = null;
    private String usage = null;
    private BiConsumer<CommandArguments, CommandContext> action = (s, c) -> {
    };

    @Nonnull
    public Builder withLabels(@Nonnull String... labels) {
      return withLabels(Set.of(labels));
    }

    @Nonnull
    public Builder withLabels(@Nonnull Set<String> labels) {
      this.labels = Set.copyOf(labels);
      return this;
    }

    @Nonnull
    public Builder withPrimaryLabel(@Nullable String label) {
      this.primaryLabel = label;
      return this;
    }

    @Nonnull
    public Builder withHelp(@Nullable String help) {
      this.help = help;
      return this;
    }

    @Nonnull
    public Builder withDescription(@Nullable String description) {
      this.description = description;
      return this;
    }

    @Nonnull
    public Builder withUsage(@Nullable String usage) {
      this.usage = usage;
      return this;
    }

    @Nonnull
    public Builder withAction(@Nonnull BiConsumer<CommandArguments, CommandContext> action) {
      this.action = action;
      return this;
    }

    @Nonnull
    @CheckReturnValue
    public CommandCallable build() {
      return new AssembledCommandCallable(labels, primaryLabel, help, description, usage, action);
    }
  }
}
