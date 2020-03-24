package io.github.mela.command.bind.map;

import io.github.mela.command.bind.TargetType;
import io.github.mela.command.core.CommandContext;
import java.lang.annotation.Annotation;
import javax.annotation.Nonnull;

public interface MappingInterceptor<T extends Annotation> {

  void preprocess(
      @Nonnull T annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  );

  void postprocess(
      @Nonnull T annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  );

  default void verify(@Nonnull T annotation, @Nonnull TargetType type) {

  }

}
