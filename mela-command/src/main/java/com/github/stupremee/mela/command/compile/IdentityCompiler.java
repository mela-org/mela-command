package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.GroupBindings;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class IdentityCompiler implements CommandCompiler {

  @Nonnull
  @Override
  public Set<? extends CommandCallable> compile(@Nonnull Object command, @Nonnull GroupBindings bindings) {
    return command instanceof CommandCallable
        ? Collections.singleton((CommandCallable) command)
        : Collections.emptySet();
  }

}
