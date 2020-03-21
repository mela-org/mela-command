package io.github.mela.command.bind.provided.mappers;

import com.google.inject.Singleton;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class CommandContextMapper implements ArgumentMapper<CommandContext> {

  @Override
  public CommandContext map(@Nonnull Arguments arguments, @Nonnull CommandContext commandContext) {
    return commandContext;
  }
}
