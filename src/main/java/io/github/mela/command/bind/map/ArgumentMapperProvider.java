package io.github.mela.command.bind.map;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public interface ArgumentMapperProvider {

  @Nonnull
  ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings);

  boolean canProvideFor(@Nonnull TargetType type);

}
