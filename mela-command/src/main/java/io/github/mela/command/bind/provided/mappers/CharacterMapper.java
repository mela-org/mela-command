package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CharacterMapper implements ArgumentMapper<Character> {

  @Override
  public Character map(@Nonnull Arguments arguments, @Nonnull ContextMap commandContext, @Nonnull ContextMap mappingContext) {
    String next = arguments.nextString();
    if (next.length() == 1) {
      return next.charAt(0);
    } else {
      throw new MappingProcessException("Invalid argument: \"" + next + "\" is not a single character");
    }
  }
}
