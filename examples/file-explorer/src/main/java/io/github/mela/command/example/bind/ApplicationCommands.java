package io.github.mela.command.example.bind;

import io.github.mela.command.bind.Command;
import io.github.mela.command.provided.interceptors.Remaining;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ApplicationCommands {

  private static final String VERSION = "1.0.0";

  @Command(
      labels = {"version", "v"},
      desc = "Displays the version of the application",
      help = "Simply type \"version\" or \"v\".",
      usage = "[version|v]"
  )
  public void displayVersion(@Remaining String ignored) {
    System.out.printf("Current version is: %s%n", VERSION);
  }

  @Command(
      labels = {"exit", "bye"},
      desc = "Exits the application",
      help = "Type \"exit\" and the programme will stop.",
      usage = "[exit|bye]"
  )
  public void exit(@Remaining String ignored) {
    System.out.println("Goodbye");
    System.exit(0);
  }
}
