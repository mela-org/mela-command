package io.github.mela.command.provided.interceptors;

import io.github.mela.command.bind.ArgumentValidationException;
import io.github.mela.command.bind.IllegalTargetTypeError;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;
import java.lang.reflect.Type;
import java.nio.charset.CharsetEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CharsetInterceptor extends MappingInterceptorAdapter<Charset> {

  private final Map<String, CharsetEncoder> encoderCache = new HashMap<>();

  @Override
  public void postprocess(
      @Nonnull Charset annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    if (process.isSet() && process.getValue() != null) {
      CharsetEncoder encoder = encoderCache.computeIfAbsent(annotation.value(),
          (n) -> java.nio.charset.Charset.forName(n).newEncoder());
      if (!encoder.canEncode((String) process.getValue())) {
        process.fail(new ArgumentValidationException("Value " + process.getValue()
            + " is not compatible with the required charset " + annotation.value()));
      }
    }
  }

  @Override
  public void verify(@Nonnull Charset annotation, @Nonnull TargetType targetType) {
    Type type = targetType.getType();
    if (type != String.class) {
      throw new IllegalTargetTypeError(type, Charset.class);
    }
  }
}
