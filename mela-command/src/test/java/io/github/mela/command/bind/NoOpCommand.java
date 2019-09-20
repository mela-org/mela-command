package io.github.mela.command.bind;

import io.github.mela.command.CommandCallable;
import io.github.mela.command.CommandContext;
import io.github.mela.command.parse.Arguments;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class NoOpCommand implements CommandCallable {

  @Override
  public void call(@Nonnull Arguments arguments, @Nonnull CommandContext context) {

  }

  @Nonnull
  @Override
  public Set<String> getLabels() {
    return Collections.emptySet();
  }

  @Override
  public String getPrimaryLabel() {
    return null;
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public String getUsage() {
    return null;
  }
}
