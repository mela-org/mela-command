package io.github.mela.command.example.core;

import com.google.common.collect.ImmutableList;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandCallable;
import io.github.mela.command.core.CommandCallableAdapter;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.CommandInput;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class HelpCommand extends CommandCallableAdapter {

  private CommandGroup group;

  public HelpCommand() {
    super(
        ImmutableList.of("help"),
        "Displays general help or help for a command",
        "Type \"help\" to see all available commands. Type \"help <command>\""
            + " to receive help for a specific command.",
        "help <command>"
    );
  }

  @Override
  public void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context) {
    if (arguments.hasNext()) {
      CommandInput helpInput = CommandInput.parse(group, arguments.remaining());
      CommandCallable command = helpInput.getCommand();
      if (command == null) {
        System.out.println("Unknown command. Type just \"help\" to see a list of commands.");
      } else {
        System.out.printf(
            "Information about command \"%s\":%nAliases: %s%nDescription: %s%nUsage: %s%n%s%n",
            command.getPrimaryLabel(), command.getLabels(), command.getDescription(),
            command.getUsage(), command.getHelp());
      }
    } else {
      System.out.println("Here is a list of all available commands:");
      System.out.println();
      group.walk(this::displayCommands);
    }
  }

  private void displayCommands(CommandGroup group) {
    for (CommandCallable command : group.getCommands()) {
      System.out.printf("%s%s - %s%n",
          group.isRoot() ? "" : group.getQualifiedName() + " ", command.getPrimaryLabel(),
          command.getDescription());
    }
  }

  public void setGroup(CommandGroup group) {
    this.group = group;
  }

}
