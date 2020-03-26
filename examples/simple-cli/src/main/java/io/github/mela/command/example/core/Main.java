package io.github.mela.command.example.core;

import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.CommandDispatcher;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.DefaultDispatcher;
import io.github.mela.command.core.ImmutableGroup;
import io.github.mela.command.core.UnknownCommandException;
import java.util.Scanner;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class Main {


  public static void main(String[] args) {
    HelpCommand helpCommand = new HelpCommand();
    CommandGroup root = ImmutableGroup.builder()
        .add(new VersionCommand())
        .add(helpCommand)
        .add(new ExitCommand())
        .group("fun", "f")
          .add(new LeetifyCommand())
          .add(new RandomiseCommand())
          .add(new MockCommand())
        .parent()
        .group("util", "u")
          .add(new EncodeCommand())
          .add(new DecodeCommand())
        .root()
        .build();
    helpCommand.setGroup(root);
    CommandDispatcher dispatcher = DefaultDispatcher.create(root);
    run(dispatcher);
  }

  private static void run(CommandDispatcher dispatcher) {
    System.out.println(
        "Welcome to simple-cli. Type \"help\" to see a list of all available commands.");
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("> ");
      String command = scanner.nextLine();
      try {
        dispatcher.dispatch(command, CommandContext.create());
      } catch (UnknownCommandException e) {
        System.out.println("Unknown command. Use \"help\" for help.");
      }
    }
  }

}
