package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.SingleStringArgument;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class DefaultInterceptor extends MappingInterceptorAdapter<Default> {

  @Override
  public void postprocess(
      @Nonnull Default annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    if (process.isErroneous() || !process.isSet() || process.getValue() == null) {
      process.fixError();
      process.requestMapping(SingleStringArgument.of(annotation.value()));
    }
  }
}