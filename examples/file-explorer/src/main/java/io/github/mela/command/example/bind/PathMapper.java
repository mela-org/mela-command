package io.github.mela.command.example.bind;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PathMapper implements ArgumentMapper<Path> {

  @Override
  public Path map(@Nonnull CommandArguments arguments, @Nonnull CommandContext commandContext) {
    Path workingDirectory = commandContext.get(Path.class, "working")
        .orElseThrow(AssertionError::new);
    if (arguments.hasNext()) {
      return workingDirectory
          .resolve(Paths.get(arguments.nextString()))
          .toAbsolutePath()
          .normalize();
    } else {
      return workingDirectory;
    }
  }
}
