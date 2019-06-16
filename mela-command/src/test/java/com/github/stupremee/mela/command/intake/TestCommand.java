package com.github.stupremee.mela.command.intake;

import com.github.stupremee.mela.command.annotation.ArgumentSize;
import com.github.stupremee.mela.command.annotation.CommandClass;
import com.github.stupremee.mela.command.annotation.Flags;
import com.sk89q.intake.Command;
import com.sk89q.intake.argument.Namespace;
import com.sk89q.intake.parametric.annotation.Optional;

import java.util.Map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@CommandClass
public class TestCommand {

  public static final String NAME = "test";

  @Command(aliases = NAME,
      desc = "No description",
      help = "Still no description")
  public void onCommand(
      @Optional CustomArgument argument,
      @ArgumentSize int size,
      @Flags Map<Character, String> flags,
      Namespace namespace
  ) {

  }

}
