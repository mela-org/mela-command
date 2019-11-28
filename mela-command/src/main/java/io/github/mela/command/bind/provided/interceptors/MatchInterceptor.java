package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.ArgumentValidationException;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MatchInterceptor extends MappingInterceptorAdapter<Match> {

  // TODO: 28.11.2019 cache regex

  @Override
  public void postprocess(@Nonnull Match annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
    if (process.getParameter().getType() == String.class && process.isSet() && !process.isErroneous()) {
      if (((String) process.getValue()).matches(annotation.value())) {
        process.fail(new ArgumentValidationException("Value " + process.getValue()
            + " does not match regex " + annotation.value(), process.getParameter()));
      }
    }
  }
}
