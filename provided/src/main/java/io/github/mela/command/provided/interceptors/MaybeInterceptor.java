package io.github.mela.command.provided.interceptors;

import com.google.common.reflect.TypeToken;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MaybeInterceptor extends MappingInterceptorAdapter<Maybe> {

  @SuppressWarnings("UnstableApiUsage")
  @Override
  public void postprocess(
      @Nonnull Maybe annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    if (process.isErroneous()) {
      process.fixError();
      TypeToken<?> type = process.getTargetType().getTypeToken();
      if (type.getType() == boolean.class) {
        process.setValue(false);
      } else if (type.isPrimitive()) {
        process.setValue(0);
      } else {
        process.setValue(null);
      }
    }
  }
}
