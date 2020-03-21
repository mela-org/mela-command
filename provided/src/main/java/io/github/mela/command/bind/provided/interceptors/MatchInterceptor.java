package io.github.mela.command.bind.provided.interceptors;

import com.google.common.collect.Maps;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.bind.ArgumentValidationException;
import io.github.mela.command.bind.IllegalTargetTypeError;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MatchInterceptor extends MappingInterceptorAdapter<Match> {

  private final Map<String, Pattern> patterns = Maps.newHashMap();

  @Override
  public void postprocess(
      @Nonnull Match annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    Type type = process.getTargetType().getType();
    if (type != String.class) {
      throw new IllegalTargetTypeError(type, Match.class);
    }

    if (!process.isErroneous()
        && process.isSet() && process.getValue() != null) {
      Pattern pattern = patterns.computeIfAbsent(annotation.value(), Pattern::compile);
      if (!pattern.matcher((String) process.getValue()).matches()) {
        process.fail(new ArgumentValidationException("Value " + process.getValue()
            + " does not match regex " + annotation.value()));
      }
    }
  }
}
