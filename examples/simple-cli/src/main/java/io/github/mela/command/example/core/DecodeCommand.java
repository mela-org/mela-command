package io.github.mela.command.example.core;

import com.google.common.collect.ImmutableList;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandCallableAdapter;
import io.github.mela.command.core.CommandContext;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class DecodeCommand extends CommandCallableAdapter {

  private static final Pattern BINARY_STRING = Pattern.compile("([01]{8})+");

  public DecodeCommand() {
    super(
        ImmutableList.of("decode"),
        "\"decodes\" the given binary numbers to a string again",
        "Type \"util decode\" and append your binary data!",
        "decode <text>"
    );
  }

  @Override
  public void call(@Nonnull CommandArguments arguments, @Nonnull CommandContext context) {
    String input = arguments.remaining();
    if (BINARY_STRING.matcher(input).matches()) {
      for (int offset = 0; offset < input.length(); offset += 8) {
        char decoded = (char) Integer.parseInt(input.substring(offset, offset + 8), 2);
        System.out.print(decoded);
      }
      System.out.println();
    } else {
      System.out.println("Invalid argument: input is not a binary ASCII representation!");
    }
  }
}
