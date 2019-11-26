package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.ArgumentException;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class BaseInterceptor extends MappingInterceptorAdapter<Base> {

  private static final Map<Type, BiFunction<String, Integer, ? extends Number>> MAPPING_FUNCTIONS
      = new HashMap<>();

  static {
    MAPPING_FUNCTIONS.put(byte.class, Byte::parseByte);
    MAPPING_FUNCTIONS.put(Byte.class, Byte::valueOf);
    MAPPING_FUNCTIONS.put(short.class, Short::parseShort);
    MAPPING_FUNCTIONS.put(Short.class, Short::valueOf);
    MAPPING_FUNCTIONS.put(int.class, Integer::parseInt);
    MAPPING_FUNCTIONS.put(Integer.class, Integer::valueOf);
    MAPPING_FUNCTIONS.put(long.class, Long::parseLong);
    MAPPING_FUNCTIONS.put(Long.class, Long::valueOf);
    MAPPING_FUNCTIONS.put(BigInteger.class, BigInteger::new);
  }

  @Override
  public void preprocess(@Nonnull Base annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
    Type type = process.getParameter().getType();
    if (MAPPING_FUNCTIONS.containsKey(type)) {
      int base = annotation.value();
      String argument = process.getArgumentToMap();
      try {
        process.setValue(MAPPING_FUNCTIONS.get(type).apply(argument, base));
      } catch (NumberFormatException e) {
        throw new ArgumentException("Invalid argument: could not convert \""
            + argument + "\" to a number (" + type + ") of base " + base, e);
      }
    }
  }
}
