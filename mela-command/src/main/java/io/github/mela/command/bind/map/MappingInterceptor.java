package io.github.mela.command.bind.map;

import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

public interface MappingInterceptor<T extends Annotation> {

  void preprocess(@Nonnull T annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context);

  void postprocess(@Nonnull T annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context);

}
