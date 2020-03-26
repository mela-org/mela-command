package io.github.mela.command.example.bind;

import io.github.mela.command.bind.Command;
import io.github.mela.command.core.CommandContext;
import java.nio.file.Path;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CdCommand {

  @Command(
      labels = "cd",
      desc = "Changes the working directory",
      help = "This command resolves the given path against the current working directory "
          + "and therefore accepts both relative and absolute paths. "
          + "If no path is given, it does nothing",
      usage = "cd <path>"
  )
  public void cd(@Existent @Directory Path path, CommandContext context) {
    context.put(Path.class, "working", path);
  }

}
