package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class RangeInterceptor extends MappingInterceptorAdapter<Range> {

  @Override
  public void postprocess(@Nonnull Range annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
    // TODO: 26.11.2019 implement
  }
}
