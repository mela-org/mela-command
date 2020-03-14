package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class StringMapper implements ArgumentMapper<String> {

  @Override
  public String map(@Nonnull String argument, @Nonnull ContextMap commandContext, @Nonnull ContextMap mappingContext) {
    return argument;
  }

}
