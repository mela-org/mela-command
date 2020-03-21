package io.github.mela.command.bind.provided.mappers;

import com.google.inject.Singleton;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class CharacterMapper implements ArgumentMapper<Character> {

  @Override
  public Character map(@Nonnull CommandArguments arguments, @Nonnull CommandContext commandContext) {
    String next = arguments.nextString();
    if (next.length() == 1) {
      return next.charAt(0);
    } else {
      throw new MappingProcessException("Invalid argument: \""
          + next + "\" is not a single character");
    }
  }
}
