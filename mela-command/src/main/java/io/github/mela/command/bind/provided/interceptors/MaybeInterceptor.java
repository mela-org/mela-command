package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.bind.parameter.GenericReflection;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MaybeInterceptor extends MappingInterceptorAdapter<Maybe> {

  @Override
  public void postprocess(@Nonnull Maybe annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
    if (process.isErroneous()) {
      process.fixError();
      process.getArguments().reset();
      Class<?> type = GenericReflection.getRaw(process.getParameter().getType());
      if (type != null && type.isPrimitive()) {
        process.setValue(0);
      } else {
        process.setValue(null);
      }
    }
  }
}
