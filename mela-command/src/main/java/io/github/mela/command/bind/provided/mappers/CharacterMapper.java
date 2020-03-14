package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CharacterMapper implements ArgumentMapper<Character> {

  @Override
  public Character map(@Nonnull String argument, @Nonnull ContextMap commandContext, @Nonnull ContextMap mappingContext) {
    if (argument.length() == 1) {
      return argument.charAt(0);
    } else {
      throw new MappingProcessException("Invalid argument: \"" + argument + "\" is not a single character");
    }
  }
}
