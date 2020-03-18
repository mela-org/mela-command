package io.github.mela.command.bind.provided.mappers;

import com.google.inject.Singleton;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class RawStringMapper implements ArgumentMapper<String> {

  @Override
  public String map(@Nonnull Arguments arguments, @Nonnull ContextMap commandContext) {
    return arguments.getRaw();
  }
}
