package com.github.stupremee.mela.command;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.BiConsumer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CallableAdapter implements CommandCallable {

  private final Set<String> labels;
  private final String help;
  private final String description;
  private final String usage;

  private final BiConsumer<String, CommandContext> action;

  private CallableAdapter(@Nonnull Set<String> labels, @Nullable String help,
                  @Nullable String description, @Nullable String usage,
                  @Nonnull BiConsumer<String, CommandContext> action) {
    this.labels = Set.copyOf(labels);
    this.help = help;
    this.description = description;
    this.usage = usage;
    this.action = checkNotNull(action);
  }

  @Override
  public void call(@Nonnull String arguments, @Nonnull CommandContext context) {
    action.accept(arguments, context);
  }

  @Nonnull
  @Override
  public Set<String> getLabels() {
    return labels;
  }

  @Override
  public String getHelp() {
    return help;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String getUsage() {
    return usage;
  }

  @Nonnull
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private Set<String> labels = Set.of();
    private String help = null;
    private String description = null;
    private String usage = null;
    private BiConsumer<String, CommandContext> action = (s, c) -> {};

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
    public Builder withAction(@Nonnull BiConsumer<String, CommandContext> action) {
      this.action = action;
      return this;
    }

    @Nonnull
    public CommandCallable build() {
      return new CallableAdapter(labels, help, description, usage, action);
    }
  }
}
