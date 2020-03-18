package io.github.mela.command.bind.provided.interceptors;

import com.google.inject.Singleton;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.ArgumentException;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.OptionalInt;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class FlagInterceptor extends MappingInterceptorAdapter<Flag> {

  @Override
  public void preprocess(@Nonnull Flag annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context) {
    Arguments arguments = process.getArguments();
    OptionalInt flagPosition = Arrays.stream(annotation.value())
        .map("-"::concat)
        .mapToInt(arguments::indexOf)
        .filter((i) -> i != -1)
        .findFirst();
    flagPosition.ifPresent((pos) -> {
      arguments.jumpTo(pos);
      arguments.nextString();
    });

    Type type = process.getTargetType().getType();
    if (type == boolean.class || type == Boolean.class) {
      process.setValue(flagPosition.isPresent());
    } else if (flagPosition.isEmpty()) {
      process.fail(new ArgumentException("Missing flag; could not find flag matching annotation " + annotation));
    }
  }

  @Override
  public void postprocess(@Nonnull Flag annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context) {
    process.getArguments().resetPosition();
  }
}
