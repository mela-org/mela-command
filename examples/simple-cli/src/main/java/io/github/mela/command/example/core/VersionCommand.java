package io.github.mela.command.example.core;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class VersionCommand extends CommandCallableAdapter {

  private static final String VERSION = "1.0.0";

  public VersionCommand() {
    super(ImmutableList.of("version", "v"),
        "Displays the version of the application", null, "\"version\" or \"v\"");
  }

  @Override
  public void call(CommandArguments arguments, CommandContext context) {
    System.out.printf("Current version is: %s%n", VERSION);
  }
}
