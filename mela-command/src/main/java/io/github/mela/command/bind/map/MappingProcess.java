package io.github.mela.command.bind.map;

import io.github.mela.command.bind.TargetType;
import io.github.mela.command.core.Arguments;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingProcess {

  private final TargetType targetType;
  private final Arguments arguments;

  private boolean isSet;
  private Throwable error;
  private Object value;
  private Arguments requestedArguments;


  private MappingProcess(TargetType targetType, Arguments arguments) {
    this.targetType = targetType;
    this.isSet = false;
    this.value = null;
    this.error = null;
    this.requestedArguments = null;
    this.arguments = arguments;
  }

  static MappingProcess create(@Nonnull TargetType targetType, @Nonnull Arguments arguments) {
    checkNotNull(targetType);
    checkNotNull(arguments);
    return new MappingProcess(targetType, arguments);
  }

  public void fail(@Nonnull Throwable error) {
    checkNotNull(error);
    if (this.error == null) {
      this.error = error;
    }
  }

  public void fixError() {
    this.error = null;
  }

  public void setValue(@Nullable Object value) {
    checkState(!isErroneous(), "You may not set a value before resolving existing errors");
    this.value = value;
    this.isSet = true;
  }

  public boolean isSet() {
    return isSet;
  }

  @Nullable
  public Object getValue() {
    checkState(isSet, "No value set!");
    return value;
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

  public Arguments getArguments() {
    return arguments;
  }

  // TODO make more easy to understand
  public Arguments getArgumentsToMap() {
    return requestedArguments != null ? requestedArguments : arguments;
  }

  public void requestMapping(Arguments arguments) {
    this.requestedArguments = arguments;
  }
}
