package io.github.mela.command.bind.map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


import io.github.mela.command.bind.TargetType;
import io.github.mela.command.core.CommandArguments;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingProcess {

  private final TargetType targetType;
  private final CommandArguments arguments;

  private boolean isSet;
  private Throwable error;
  private Object value;
  private CommandArguments requestedArguments;

  private MappingProcess(TargetType targetType, CommandArguments arguments) {
    this.targetType = targetType;
    this.isSet = false;
    this.value = null;
    this.error = null;
    this.requestedArguments = null;
    this.arguments = arguments;
  }

  static MappingProcess create(
      @Nonnull TargetType targetType, @Nonnull CommandArguments arguments) {
    checkNotNull(targetType);
    checkNotNull(arguments);
    return new MappingProcess(targetType, arguments);
  }

  public void fail(@Nonnull Throwable error) {
    checkNotNull(error);
    if (this.error == null) {
      this.error = error;
      this.isSet = false;
    }
  }

  public void fixError() {
    this.error = null;
  }

  public boolean isSet() {
    return isSet;
  }

  @Nullable
  public Object getValue() {
    checkState(isSet, "No value set!");
    return value;
  }

  public void setValue(@Nullable Object value) {
    checkState(!isErroneous(),
        "You may not set a value before resolving existing errors");
    this.value = value;
    this.isSet = true;
  }

  public boolean isErroneous() {
    return error != null;
  }

  @Nonnull
  public Throwable getError() {
    checkState(isErroneous(), "This process is not erroneous");
    return error;
  }

  @Nonnull
  public TargetType getTargetType() {
    return targetType;
  }

  @Nonnull
  public CommandArguments getArguments() {
    return arguments;
  }

  // TODO make more easy to understand
  @Nonnull
  public CommandArguments getArgumentsToMap() {
    return requestedArguments != null ? requestedArguments : arguments;
  }

  public void requestMapping(@Nonnull CommandArguments arguments) {
    this.requestedArguments = checkNotNull(arguments);
  }
}
