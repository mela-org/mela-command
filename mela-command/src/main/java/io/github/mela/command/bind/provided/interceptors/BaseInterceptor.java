package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class BaseInterceptor extends MappingInterceptorAdapter<Base> {

  private static final Set<Type> TYPES =
      Set.of(
          byte.class, Byte.class, short.class, Short.class,
          int.class, Integer.class, long.class, Long.class, BigInteger.class
      );

  @Override
  public void preprocess(@Nonnull Base annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context) {
    Type type = process.getTargetType().getKey().getType();
    if (TYPES.contains(type)) {
      process.getContext().put(int.class, "base", annotation.value());
    }
  }
}
