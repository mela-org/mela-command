package io.github.mela.command.provided.mappers.providers;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class NeverReachMapperProvider implements ArgumentMapperProvider {

  private final Predicate<TargetType> predicate;

  public NeverReachMapperProvider(Predicate<TargetType> predicate) {
    this.predicate = predicate;
  }

  @Nonnull
  @Override
  public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
    return (a, c) -> {
      throw new AssertionError("This ArgumentMapper was never supposed to be reached");
    };
  }

  @Override
  public boolean canProvideFor(@Nonnull TargetType type) {
    return predicate.test(type);
  }
}
