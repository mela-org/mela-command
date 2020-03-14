package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ContextInterceptor extends MappingInterceptorAdapter<Context> {

  @Override
  public void preprocess(@Nonnull Context annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context) {
    Type type = process.getTargetType().getKey().getType();
    String key = annotation.value();
    context.get(type, key)
        .ifPresentOrElse(
            process::setValue,
            () -> process.fail(new MissingContextException("Context of type " + type
                + " and key " + key + " is missing"))
        );
  }
}