package io.github.mela.command.bind.provided;

import io.github.mela.command.bind.guice.CommandModule;
import io.github.mela.command.bind.provided.mappers.*;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PrimitivesModule extends CommandModule {

  @Override
  protected void configure() {
    super.configure();
    bindMapper(String.class).toInstance(new StringMapper());
    bindMapper(byte.class).toInstance(new IntegerMapper<>(byte.class, Byte::parseByte));
    bindMapper(Byte.class).toInstance(new IntegerMapper<>(Byte.class, Byte::valueOf));
    bindMapper(short.class).toInstance(new IntegerMapper<>(short.class, Short::parseShort));
    bindMapper(Short.class).toInstance(new IntegerMapper<>(Short.class, Short::valueOf));
    bindMapper(int.class).toInstance(new IntegerMapper<>(int.class, Integer::parseInt));
    bindMapper(Integer.class).toInstance(new IntegerMapper<>(Integer.class, Integer::valueOf));
    bindMapper(long.class).toInstance(new IntegerMapper<>(long.class, Long::parseLong));
    bindMapper(Long.class).toInstance(new IntegerMapper<>(Long.class, Long::valueOf));
    bindMapper(float.class).toInstance(new FloatingPointMapper<>(float.class, Float::parseFloat));
    bindMapper(Float.class).toInstance(new FloatingPointMapper<>(Float.class, Float::valueOf));
    bindMapper(double.class).toInstance(new FloatingPointMapper<>(double.class, Double::parseDouble));
    bindMapper(Double.class).toInstance(new FloatingPointMapper<>(Double.class, Double::valueOf));
    bindMapper(BigInteger.class).toInstance(new IntegerMapper<>(BigInteger.class, BigInteger::new));
    bindMapper(BigDecimal.class).toInstance(new FloatingPointMapper<>(BigDecimal.class, BigDecimal::new));
    CharacterMapper characterMapper = new CharacterMapper();
    bindMapper(char.class).toInstance(characterMapper);
    bindMapper(Character.class).toInstance(characterMapper);
  }
}
