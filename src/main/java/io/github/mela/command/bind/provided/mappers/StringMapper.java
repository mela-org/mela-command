package io.github.mela.command.bind.provided.mappers;

import com.google.inject.Singleton;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class StringMapper implements ArgumentMapper<String> {

  @Override
  public String map(@Nonnull CommandArguments arguments, @Nonnull CommandContext commandContext) {
    return arguments.nextString();
  }

}
