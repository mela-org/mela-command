package io.github.mela.command.bind.provided.mappers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.CommandInput;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class CommandInputMapper implements ArgumentMapper<CommandInput> {

  private final CommandGroup root;

  // TODO see if this even works (cyclic dependency?)
  @Inject
  public CommandInputMapper(@Nonnull CommandGroup root) {
    this.root = checkNotNull(root);
  }

  @Override
  public CommandInput map(@Nonnull Arguments arguments, @Nonnull CommandContext commandContext) {
    return CommandInput.parse(root, arguments.nextString());
  }
}
