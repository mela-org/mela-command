package io.github.mela.command.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.ArgumentException;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.OptionalInt;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class FlagInterceptor extends MappingInterceptorAdapter<Flag> {

  @Override
  public void preprocess(
      @Nonnull Flag annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    CommandArguments arguments = process.getArguments();
    OptionalInt flagPosition = Arrays.stream(annotation.value())
        .map("-"::concat)
        .mapToInt(arguments::indexOfWord)
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
      process.fail(new ArgumentException("Missing flag; could not find flag matching annotation "
          + annotation));
    }
  }

  @Override
  public void postprocess(
      @Nonnull Flag annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    process.getArguments().resetPosition();
  }
}
