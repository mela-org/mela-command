package io.github.mela.command.example.core;

import com.google.common.collect.ImmutableList;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandCallableAdapter;
import io.github.mela.command.core.CommandContext;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MockCommand extends CommandCallableAdapter {

  public MockCommand() {
    super(
        ImmutableList.of("mock", "spongebob"),
        "\"mocks\" the text like the spongebob meme, e.g. \"hello world\" -> \"hELlO WoRLd\"",
        "Type \"fun mock\" and append your piece of text!",
        "mock/spongebob <text>"
    );
  }

  @Override
  public void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context) {
    while (arguments.hasNext()) {
      char next = arguments.next();
      boolean uppercase = ThreadLocalRandom.current().nextBoolean();
      System.out.print(uppercase ? Character.toUpperCase(next) : Character.toLowerCase(next));
    }
    System.out.println();
  }
}
