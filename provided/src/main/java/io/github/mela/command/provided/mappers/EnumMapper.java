package io.github.mela.command.provided.mappers;

import static com.google.common.base.Preconditions.checkNotNull;


import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class EnumMapper<T extends Enum<T>> implements ArgumentMapper<T> {

  private final Class<T> enumType;

  public EnumMapper(@Nonnull Class<T> enumType) {
    this.enumType = checkNotNull(enumType);
  }

  @Override
  public T map(@Nonnull CommandArguments arguments, @Nonnull CommandContext commandContext) {
    return Enum.valueOf(enumType, arguments.nextString());
  }
}
