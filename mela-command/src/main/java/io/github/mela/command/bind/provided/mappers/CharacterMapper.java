package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.MappingException;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CharacterMapper implements ArgumentMapper<Character> {

  @Override
  public Character map(@Nonnull String argument, @Nonnull CommandContext context) {
    if (argument.length() == 1) {
      return argument.charAt(0);
    } else {
      throw new MappingException("Invalid argument: \"" + argument + "\" is not a single character");
    }
  }
}
