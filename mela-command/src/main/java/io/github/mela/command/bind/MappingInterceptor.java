package io.github.mela.command.bind;

import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

public interface MappingInterceptor<T extends Annotation> {

  void preprocess(@Nonnull T annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context);

  void postprocess(@Nonnull T annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context);

}
