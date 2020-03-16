package io.github.mela.command.bind.provided.mappers.providers;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class FlagMapperProvider implements ArgumentMapperProvider {

  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {

  }

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return type.getTypeToken().getRawType() == FlagValue.class;
  }
}
