package com.github.stupremee.mela.command.bind.parameter;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.ArgumentMapper;
import com.github.stupremee.mela.command.bind.MappingInterceptor;
import com.github.stupremee.mela.command.bind.process.ArgumentChain;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
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
