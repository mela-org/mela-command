package io.github.mela.command.bind.provided.interceptors;

import com.google.common.reflect.TypeToken;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MaybeInterceptor extends MappingInterceptorAdapter<Maybe> {

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public void postprocess(@Nonnull Maybe annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context) {
    if (process.isErroneous()) {
      process.fixError();
      process.getArguments().reset();
      TypeToken<?> type = process.getTargetType().getTypeToken();
      if (type.isPrimitive()) {
        process.setValue(0);
      } else {
        process.setValue(null);
      }
    }
  }
}
