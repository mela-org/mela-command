package io.github.mela.command.bind.map;

import io.github.mela.command.bind.TargetType;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingProcess {

  private final ContextMap context;
  private final TargetType targetType;

  private boolean isSet;
  private Throwable error;
  private Object value;
  private String argumentToMap;

  private MappingProcess(TargetType targetType) {
    this.targetType = targetType;
    this.isSet = false;
    this.value = null;
    this.error = null;
    this.argumentToMap = null;
    this.context = ContextMap.create();
  }

  public static MappingProcess create(@Nonnull TargetType targetType) {
    checkNotNull(targetType);
    return new MappingProcess(targetType);
  }

  public void fail(@Nonnull Throwable error) {
    checkNotNull(error);
    if (this.error == null) {
      this.error = error;
    }
  }

  public void reset() {
    this.value = null;
    this.isSet = false;
    this.error = null;
    this.argumentToMap = null;
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
  public String getArgumentToMap() {
    checkState(argumentToMap != null, "There is no argument to map");
    return argumentToMap;
  }

  public boolean hasArgumentToMap() {
    return argumentToMap != null;
  }

  public void setArgumentToMap(@Nonnull String argumentToMap) {
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
}
