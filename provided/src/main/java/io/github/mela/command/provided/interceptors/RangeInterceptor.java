package io.github.mela.command.provided.interceptors;

import io.github.mela.command.bind.ArgumentValidationException;
import io.github.mela.command.bind.IllegalTargetTypeError;
import io.github.mela.command.bind.PreconditionError;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;
import java.lang.reflect.Type;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class RangeInterceptor extends MappingInterceptorAdapter<Range> {

  @Override
  public void postprocess(
      @Nonnull Range annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    if (process.isSet() && process.getValue() != null) {
      int value = (int) process.getValue();
      int from = annotation.from();
      int to = annotation.to();
      if (value < from || value >= to) {
        process.fail(ArgumentValidationException.create("Invalid argument; Value " + value
            + " is out of range " + from + ".." + to, process.getTargetType().getType(), value));
      }
    }
  }

  @Override
  public void verify(@Nonnull Range annotation, @Nonnull TargetType targetType) {
    Type type = targetType.getType();
    if (type != int.class && type != Integer.class) {
      throw new IllegalTargetTypeError(type, Range.class);
    } else if (annotation.from() >= annotation.to()) {
      throw new PreconditionError(annotation
          + " is not a valid range; from must be smaller than to.");
    }
  }
}
