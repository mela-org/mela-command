package io.github.mela.command.bind.map;

import io.github.mela.command.bind.Arguments;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingProcess {

  private final ContextMap context;
  private final TargetType targetType;
  private final Arguments arguments;

  private boolean isSet;
  private Throwable error;
  private Object value;
  private Supplier<String> argumentToMap;

  private MappingProcess(TargetType targetType, Arguments arguments) {
    this.targetType = targetType;
    this.arguments = arguments;
    this.isSet = false;
    this.value = null;
    this.error = null;
    this.argumentToMap = null;
    this.context = ContextMap.create();
  }

  public static MappingProcess create(@Nonnull TargetType targetType, @Nonnull Arguments arguments) {
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

  public void unset() {
    this.isSet = false;
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
  public String consumeArgumentToMap() {
    checkState(argumentToMap != null, "There is no argument to map");
    String argument = argumentToMap.get();
    argumentToMap = null;
    return argument;
  }

  public boolean hasArgumentToMap() {
    return argumentToMap != null;
  }

  public void setArgumentToMap(@Nonnull Supplier<String> argumentToMap) {
    this.argumentToMap = checkNotNull(argumentToMap);
  }

  @Nonnull
  public ContextMap getContext() {
    return context;
  }

  @Nonnull
  public TargetType getTargetType() {
    return targetType;
  }

  @Nonnull
  public Arguments getArguments() {
    return arguments;
  }
}
