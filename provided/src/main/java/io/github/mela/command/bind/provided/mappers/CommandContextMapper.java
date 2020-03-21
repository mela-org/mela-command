package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandContextMapper implements ArgumentMapper<CommandContext> {

  @Override
  public CommandContext map(@Nonnull CommandArguments arguments, @Nonnull CommandContext commandContext) {
    return commandContext;
  }
}
