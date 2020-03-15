package io.github.mela.command.core;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.BiConsumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class AssembledCommandCallable extends CommandCallableAdapter {

  private final BiConsumer<String, ContextMap> action;

  private AssembledCommandCallable(@Nonnull Set<String> labels, @Nullable String primaryLabel, @Nullable String help,
                                   @Nullable String description, @Nullable String usage,
                                   @Nonnull BiConsumer<String, ContextMap> action) {
    super(labels, primaryLabel, description, help, usage);
    this.action = checkNotNull(action);
  }

  @Override
  public void call(@Nonnull String arguments, @Nonnull ContextMap context) {
    action.accept(arguments, context);
  }

  @Nonnull
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private Set<String> labels = Set.of();
    private String primaryLabel = null;
    private String help = null;
    private String description = null;
    private String usage = null;
    private BiConsumer<String, ContextMap> action = (s, c) -> {};

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
    public Builder withAction(@Nonnull BiConsumer<String, ContextMap> action) {
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
