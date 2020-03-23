package io.github.mela.command.provided.mappers;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.inject.Inject;
import io.github.mela.command.bind.PreconditionError;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.CommandInput;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandInputMapper implements ArgumentMapper<CommandInput> {

  private CommandGroup group;

  @Inject
  public void setGroup(@Nonnull CommandGroup group) {
    this.group = checkNotNull(group);
  }

  @Override
  public CommandInput map(@Nonnull CommandArguments arguments,
                          @Nonnull CommandContext commandContext) {
    if (group == null) {
      throw new IllegalStateException(
          "No group has been set for CommandInputMapper, it can therefore not be used");
    }
    return CommandInput.parse(group, arguments.nextString());
  }
}
