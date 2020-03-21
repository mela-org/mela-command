package io.github.mela.command.bind;

import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import java.lang.annotation.Annotation;
import javax.annotation.Nonnull;

@FunctionalInterface
public interface CommandInterceptor<T extends Annotation> {

  void intercept(@Nonnull T annotation,
                 @Nonnull CommandArguments arguments,
                 @Nonnull CommandContext context);

}
