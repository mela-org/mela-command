package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

public interface MappingInterceptor<T extends Annotation> {

  void preprocess(@Nonnull T annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context);

  void postprocess(@Nonnull T annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context);

}
