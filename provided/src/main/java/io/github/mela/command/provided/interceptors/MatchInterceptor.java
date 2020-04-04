package io.github.mela.command.provided.interceptors;

import com.google.common.collect.Maps;
import io.github.mela.command.bind.TargetType;
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

  private final Map<String, Pattern> patternCache = Maps.newHashMap();

  @Override
  public void postprocess(
      @Nonnull Match annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    if (process.isSet() && process.getValue() != null) {
      String regex = annotation.value();
      Pattern pattern = patternCache.computeIfAbsent(regex, Pattern::compile);
      String value = (String) process.getValue();
      if (!pattern.matcher(value).matches()) {
        process.fail(ArgumentValidationException.create("Invalid argument; Value " + value
            + " does not match regex " + regex, String.class, value));
      }
    }
  }

  @Override
  public void verify(@Nonnull Match annotation, @Nonnull TargetType targetType) {
    Type type = targetType.getType();
    if (type != String.class) {
      throw new IllegalTargetTypeError(type, Match.class);
    }
  }
}
