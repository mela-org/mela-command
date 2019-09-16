package com.github.stupremee.mela.command.bind.parameter;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.MappingInterceptor;
import com.github.stupremee.mela.command.bind.ArgumentMapper;
import com.github.stupremee.mela.command.bind.process.ArgumentChain;
import com.github.stupremee.mela.command.bind.process.MappingProcess;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandParameter {

  private final Map<Annotation, MappingInterceptor> interceptors;
  private final ArgumentMapper<?> mapper;
  private final Type type;
  private final String name;

  public CommandParameter(@Nonnull Parameter reflectiveParameter, @Nonnull Map<Annotation, MappingInterceptor> interceptors, @Nonnull ArgumentMapper<?> mapper) {
    this.interceptors = Map.copyOf(interceptors);
    this.mapper = checkNotNull(mapper);
    this.type = reflectiveParameter.getParameterizedType();
    this.name = reflectiveParameter.getName();
  }

  public Object advance(@Nonnull ArgumentChain chain, @Nonnull CommandContext context) throws Throwable {
    MappingProcess process = new MappingProcess(this, mapper, chain, context);
    return process(process, context);
  }

  private Object process(MappingProcess process, CommandContext context) throws Throwable {
    interceptBefore(process, context);
    if (!process.isSet()) {
      mapNextArgument(process, context);
    }
    interceptAfter(process, context);

    if (process.isErroneous()) {
      throw process.getError();
    } else if (!process.isSet()) {
      throw new RuntimeException("Unsatisfied parameter value");
    } else {
      return process.getValue();
    }
  }

  private void mapNextArgument(MappingProcess process, CommandContext context) {
    try {
      process.setValue(mapper.map(process.consume(), context));
    } catch (Throwable throwable) {
      process.fail(throwable);
    }
  }

  private void interceptBefore(MappingProcess process, CommandContext context) {
    interceptors.forEach((annotation, interceptor) -> {
      try {
        interceptor.preprocess(annotation, process, context);
      } catch (Throwable t) {
        process.fail(t);
      }
    });
  }

  private void interceptAfter(MappingProcess process, CommandContext context) {
    interceptors.forEach((annotation, interceptor) -> {
      try {
        interceptor.postprocess(annotation, process, context);
      } catch (Throwable t) {
        process.fail(t);
      }
    });
  }

  public String getName() {
    return name;
  }

  public Type getType() {
    return type;
  }
}
