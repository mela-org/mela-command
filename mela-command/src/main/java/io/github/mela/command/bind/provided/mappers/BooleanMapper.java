package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class BooleanMapper implements ArgumentMapper<Boolean> {

  @Override
  public Boolean map(@Nonnull Arguments arguments, @Nonnull ContextMap commandContext) {
    return Boolean.valueOf(arguments.nextString());
  }

}
