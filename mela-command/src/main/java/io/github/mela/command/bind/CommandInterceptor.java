package io.github.mela.command.bind;

import io.github.mela.command.bind.map.ArgumentChain;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

public interface CommandInterceptor<T extends Annotation> {

  boolean intercept(@Nonnull T annotation, @Nonnull ArgumentChain arguments, @Nonnull CommandContext context);

}
