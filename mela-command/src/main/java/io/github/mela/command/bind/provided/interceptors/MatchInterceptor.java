package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.bind.provided.ArgumentValidationException;
import io.github.mela.command.bind.provided.IllegalTargetTypeError;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MatchInterceptor extends MappingInterceptorAdapter<Match> {

  // TODO: 28.11.2019 cache regex

  @Override
  public void postprocess(@Nonnull Match annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context) {
    Type type = process.getTargetType().getType();
    if (type != String.class) {
      throw new IllegalTargetTypeError(type, Match.class);
    }

    if (!process.isErroneous()
        && process.isSet() && process.getValue() != null) {
      if (((String) process.getValue()).matches(annotation.value())) {
        process.fail(new ArgumentValidationException("Value " + process.getValue()
            + " does not match regex " + annotation.value()));
      }
    }
  }
}
