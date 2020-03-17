package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.bind.provided.IllegalTargetTypeError;
import io.github.mela.command.bind.provided.PreconditionError;
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
    Type type = process.getTargetType().getType();
    if (TYPES.contains(type)) {
      int base = annotation.value();
      if (base < Character.MIN_RADIX || base > Character.MAX_RADIX) {
        throw new PreconditionError("Illegal @Base interceptor annotation value: " + base
            + " (base must not be greater than Character.MAX_RADIX or smaller than Character.MIN_RADIX).");
      }
      process.getContext().put(int.class, "base", base);
    } else {
      throw new IllegalTargetTypeError(type, Base.class);
    }
  }
}
