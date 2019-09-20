package io.github.mela.command.bind.parameter;

import io.github.mela.command.CommandContext;
import io.github.mela.command.bind.ArgumentMapper;
import io.github.mela.command.bind.MappingInterceptor;
import io.github.mela.command.bind.process.ArgumentChain;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CollectionParameter extends CommandParameter {

  public CollectionParameter(Type type, String name, String description, Map<Annotation, MappingInterceptor> interceptors, ArgumentMapper<?> mapper) {
    super(type, name, description, interceptors, mapper);
  }

  @Override
  public Object advance(@Nonnull ArgumentChain chain, @Nonnull CommandContext context) throws Throwable {
    List<Object> objects = new ArrayList<>();
    while (chain.hasNext()) {
      objects.add(super.advance(chain, context));
    }
    return List.copyOf(objects);
  }
}
