package io.github.mela.command.example.core;

import com.google.common.collect.ImmutableList;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandCallableAdapter;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ExitCommand extends CommandCallableAdapter {

  public ExitCommand() {
    super(
        ImmutableList.of("exit", "bye"),
        "Exits the application",
        "Type \"exit\" and the programme will stop.",
        "[exit|bye]"
    );
  }

  @Override
  public void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context) {
    System.out.println("Goodbye");
    System.exit(0);
  }
}
