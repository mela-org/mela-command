package io.github.mela.command.provided.interceptors;

import io.github.mela.command.bind.PreconditionError;
import io.github.mela.command.bind.TargetType;
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
    String[] names = annotation.value();
    String prefix = annotation.prefix();
    OptionalInt flagPosition = Arrays.stream(names)
        .map(prefix::concat)
        .mapToInt(arguments::indexOfWord)
        .filter((i) -> i != -1)
        .findFirst();

    flagPosition.ifPresent((pos) -> {
      arguments.setPosition(pos);
      arguments.nextString();
    });

    Type type = process.getTargetType().getType();
    if (type == boolean.class || type == Boolean.class) {
      process.setValue(flagPosition.isPresent());
    } else if (!flagPosition.isPresent()) {
      process.fail(ArgumentException.create("Missing flag: " + names[0], arguments));
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

  @Override
  public void verify(@Nonnull Flag annotation, @Nonnull TargetType type) {
    if (annotation.value().length == 0) {
      throw new PreconditionError("Invalid flag annotation " + annotation
          + ": Flag values must not be empty");
    }
  }
}
