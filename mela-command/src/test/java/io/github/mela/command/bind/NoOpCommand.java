package io.github.mela.command.bind;

<<<<<<< HEAD:mela-command/src/test/java/com/github/stupremee/mela/command/bind/NoOpCommand.java
import com.github.stupremee.mela.command.core.CommandCallable;
import com.github.stupremee.mela.command.core.CommandContext;
import com.github.stupremee.mela.command.core.parse.Arguments;
=======
import io.github.mela.command.CommandCallable;
import io.github.mela.command.CommandContext;
import io.github.mela.command.parse.Arguments;
>>>>>>> 128ccf988eb675eabc1b310c2c0da95b0e6d2ee8:mela-command/src/test/java/io/github/mela/command/bind/NoOpCommand.java

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
