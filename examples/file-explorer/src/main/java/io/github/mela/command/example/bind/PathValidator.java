package io.github.mela.command.example.bind;

import io.github.mela.command.bind.ArgumentValidationException;
import io.github.mela.command.bind.IllegalTargetTypeError;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class PathValidator<T extends Annotation> extends MappingInterceptorAdapter<T> {

  private final Predicate<Path> predicate;
  private final String errorMessage;

  public PathValidator(Predicate<Path> predicate, String errorMessage) {
    this.predicate = predicate;
    this.errorMessage = errorMessage;
  }

  @Override
  public void postprocess(
      @Nonnull T annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    if (process.isSet() && process.getValue() != null) {
      if (!predicate.test((Path) process.getValue())) {
        throw new ArgumentValidationException("Invalid path; " + errorMessage);
      }
    }
  }

  @Override
  public void verify(@Nonnull T annotation, @Nonnull TargetType type) {
    if (type.getType() != Path.class) {
      throw new IllegalTargetTypeError(type.getType(), annotation.annotationType());
    }
  }
}
