package io.github.mela.command.bind.parameter;

import io.github.mela.command.bind.map.ArgumentChain;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingInterceptor;
import io.github.mela.command.core.CommandContext;

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

  CollectionParameter(Type type, String name, String description, Map<Annotation, MappingInterceptor> interceptors, ArgumentMapper<?> mapper) {
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
