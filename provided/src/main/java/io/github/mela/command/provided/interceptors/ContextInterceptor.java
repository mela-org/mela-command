package io.github.mela.command.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;
import java.lang.reflect.Type;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ContextInterceptor extends MappingInterceptorAdapter<Context> {

  @Override
  public void preprocess(
      @Nonnull Context annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    Type type = process.getTargetType().getType();
    String key = annotation.value();
    Optional<?> value = context.get(type, key);
    if (value.isPresent()) {
      process.setValue(value.get());
    } else {
      process.fail(MissingContextException.create(
          "Missing context value: could not find context of type "
              + type + " and key " + key, type, key));
    }
  }
}
