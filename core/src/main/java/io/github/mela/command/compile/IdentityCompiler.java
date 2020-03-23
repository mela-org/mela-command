package io.github.mela.command.compile;

import com.google.inject.Singleton;
import io.github.mela.command.core.CommandCallable;
import java.util.Collections;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public final class IdentityCompiler implements CommandCompiler {

  @Nonnull
  @Override
  public Set<? extends CommandCallable> compile(@Nonnull Object command) {
    if (!(command instanceof CommandCallable)) {
      throw new CommandCompilerException("Could not compile command using IdentityCompiler: Object "
          + command + " is not an instance of CommandCallable");
    }
    return Collections.singleton((CommandCallable) command);
  }

}
