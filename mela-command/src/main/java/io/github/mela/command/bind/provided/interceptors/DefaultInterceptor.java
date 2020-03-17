package io.github.mela.command.bind.provided.interceptors;

import com.google.inject.Singleton;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class DefaultInterceptor extends MappingInterceptorAdapter<Default> {

  @Override
  public void postprocess(@Nonnull Default annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context) {
    if (process.isErroneous() || !process.isSet() || process.getValue() == null) {
      process.fixError();
      process.requestMapping(Arguments.of(annotation.value()));
    }
  }
}
