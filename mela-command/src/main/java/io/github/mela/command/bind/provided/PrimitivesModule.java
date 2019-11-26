package io.github.mela.command.bind.provided;

import io.github.mela.command.bind.guice.CommandModule;
import io.github.mela.command.bind.provided.mappers.CharacterMapper;
import io.github.mela.command.bind.provided.mappers.NumberMapper;
import io.github.mela.command.bind.provided.mappers.StringMapper;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PrimitivesModule extends CommandModule {

  @Override
  protected void configure() {
    super.configure();
    bindMapper(String.class).toInstance(new StringMapper());
    bindMapper(byte.class).toInstance(new NumberMapper<>(byte.class, Byte::parseByte));
    bindMapper(Byte.class).toInstance(new NumberMapper<>(Byte.class, Byte::valueOf));
    bindMapper(short.class).toInstance(new NumberMapper<>(short.class, Short::parseShort));
    bindMapper(Short.class).toInstance(new NumberMapper<>(Short.class, Short::valueOf));
    bindMapper(int.class).toInstance(new NumberMapper<>(int.class, Integer::parseInt));
    bindMapper(Integer.class).toInstance(new NumberMapper<>(Integer.class, Integer::valueOf));
    bindMapper(long.class).toInstance(new NumberMapper<>(long.class, Long::parseLong));
    bindMapper(Long.class).toInstance(new NumberMapper<>(Long.class, Long::valueOf));
    bindMapper(char.class).toInstance(new CharacterMapper());
    bindMapper(Character.class).toInstance(new CharacterMapper());
  }
}
