package io.github.mela.command.example.bind;

import io.github.mela.command.bind.Command;
import io.github.mela.command.core.CommandCallable;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.CommandInput;
import io.github.mela.command.provided.interceptors.Remaining;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class HelpCommand {

  @Command(
      labels = "help",
      desc = "Displays general help or help for a command",
      help = "Type \"help\" to see all available commands. Type \"help <command>\""
          + " to receive help for a specific command.",
      usage = "help <command>"
  )
  public void help(@Remaining CommandInput input) {
    CommandCallable command = input.getCommand();
    if (command == null) {
      CommandGroup group = input.getGroup();
      System.out.print("Here is a list of all available commands");
      System.out.println(group.isRoot() ? ":" : " in group " + group.getQualifiedName() + ":");
      group.walk(this::displayCommands);
    } else {
      System.out.printf(
          "Information about command \"%s\":%nAliases: %s%nDescription: %s%nUsage: %s%n%s%n",
          command.getPrimaryLabel(), command.getLabels(), command.getDescription(),
          command.getUsage(), command.getHelp());
    }

  }

  private void displayCommands(CommandGroup group) {
    for (CommandCallable command : group.getCommands()) {
      System.out.printf("%s%s - %s%n",
          group.isRoot() ? "" : group.getQualifiedName() + " ", command.getPrimaryLabel(),
          command.getDescription());
    }
  }

}
