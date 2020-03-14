package io.github.mela.command.bind;

import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

@FunctionalInterface
public interface CommandInterceptor<T extends Annotation> {

  boolean intercept(@Nonnull T annotation, @Nonnull Arguments arguments, @Nonnull ContextMap context);

}
