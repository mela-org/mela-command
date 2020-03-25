package io.github.mela.command.example.core;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableList;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandCallableAdapter;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class EncodeCommand extends CommandCallableAdapter {

  public EncodeCommand() {
    super(
        ImmutableList.of("encode"),
        "\"encodes\" the given ASCII text in binary",
        "Type \"util encode\" and append your piece of text!",
        "encode <text>"
    );
  }

  @Override
  public void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context) {
    String text = arguments.remaining();
    if (CharMatcher.ascii().matchesAllOf(text)) {
      for (char c : text.toCharArray()) {
        String binary = Integer.toBinaryString(c);
        for (int padding = binary.length(); padding < 8; padding++) {
          System.out.print(0);
        }
        System.out.print(binary);
      }
      System.out.println();
    } else {
      System.out.println("Invalid argument! Text must contain ASCII characters only.");
    }
  }
}
