package io.github.mela.command.bind.map;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public interface ArgumentMapperProvider {

  ArgumentMapper<?> provideFor(TargetType type, CommandBindings bindings);

  boolean canProvideFor(TargetType type);

}
