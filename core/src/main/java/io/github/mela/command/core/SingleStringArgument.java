package io.github.mela.command.core;

import static com.google.common.base.Preconditions.checkNotNull;


import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class SingleStringArgument extends CommandArguments {

  protected SingleStringArgument(String arguments) {
    super(arguments);
  }

  public static CommandArguments of(@Nonnull String arguments) {
    return new SingleStringArgument(checkNotNull(arguments));
  }

  @Override
  public String nextString() {
    while (hasNext()) {
      next();
    }
    return getRaw();
  }
}
