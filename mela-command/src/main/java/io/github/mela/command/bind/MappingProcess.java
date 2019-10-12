package io.github.mela.command.bind;

import io.github.mela.command.bind.parameter.CommandParameter;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.parse.Arguments;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MappingProcess {

  private final CommandParameter parameter;
  private final ArgumentMapper mapper;
  private final ArgumentChain chain;
  private final CommandContext context;

  private int argumentsConsumed;
  private boolean isSet;

  private Throwable error;
  private Object value;

  public MappingProcess(@Nonnull CommandParameter parameter, @Nonnull ArgumentMapper mapper, @Nonnull ArgumentChain chain, @Nonnull CommandContext context) {
    this.mapper = mapper;
    this.chain = checkNotNull(chain);
    this.parameter = checkNotNull(parameter);
    this.context = checkNotNull(context);
    this.argumentsConsumed = 0;
    this.isSet = false;
    this.value = null;
    this.error = null;
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

  @Nullable
  public Object map(@Nonnull String input, @Nonnull CommandContext context) {
    return mapper.map(input, context);
  }

  public void setValue(@Nullable Object value) {
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
    checkState(isSet, "No value has been set yet");
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
  public String consume() {
    String next = chain.consume();
    ++argumentsConsumed;
    return next;
  }

  public void resetConsumption() {
    for (int i = 0; i < argumentsConsumed; i++) {
      chain.back();
    }
  }

  public int argumentsConsumed() {
    return argumentsConsumed;
  }

  @Nonnull
  public Arguments getCompleteArguments() {
    return chain.getArguments();
  }

  @Nonnull
  public CommandContext getContext() {
    return context;
  }

  public CommandParameter getParameter() {
    return parameter;
  }
}
