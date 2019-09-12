package com.github.stupremee.mela.command.bind.process;

import com.github.stupremee.mela.command.bind.parameter.CommandParameter;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingProcess {

  private final CommandParameter parameter;

  private Throwable error = null;
  private Object value = null;
  private boolean consuming = false;

  public MappingProcess(CommandParameter parameter) {
    this.parameter = parameter;
  }

  public void fail(@Nonnull Throwable error) {
    checkNotNull(error);
    if (this.error == null) {
      this.error = error;
    }
  }

  public void fix() {
    this.error = null;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public Object getValue() {
    return value;
  }

  public boolean isSuccessful() {
    return error == null;
  }

  public Throwable getError() {
    return error;
  }

  public void setConsuming(boolean consuming) {
    this.consuming = consuming;
  }

  public boolean isConsuming() {
    return consuming;
  }

  public CommandParameter getParameter() {
    return parameter;
  }
}
