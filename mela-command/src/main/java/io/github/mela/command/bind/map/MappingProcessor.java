package io.github.mela.command.bind.map;

import io.github.mela.command.bind.Arguments;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.parameter.InvalidTypeException;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MappingProcessor {

  private final TargetType type;
  private final ArgumentMapper<?> mapper;
  private final Map<Annotation, MappingInterceptor> interceptors;

  private MappingProcessor(TargetType type, ArgumentMapper<?> mapper, Map<Annotation, MappingInterceptor> interceptors) {
    this.type = type;
    this.mapper = mapper;
    this.interceptors = interceptors;
  }

  public static MappingProcessor fromParameter(CommandBindings bindings, Parameter parameter) {
    return create(bindings, parameter.getAnnotatedType(), new LinkedHashSet<>(Arrays.asList(parameter.getAnnotations())));
  }

  public static MappingProcessor fromAnnotatedType(CommandBindings bindings, AnnotatedType type) {
    return create(bindings, type, new LinkedHashSet<>());
  }

  private static MappingProcessor create(CommandBindings bindings, AnnotatedType type, Set<Annotation> annotations) {
    TargetType targetType = TargetType.create(type);
    annotations.addAll(Arrays.asList(type.getAnnotations()));
    ArgumentMapper mapper = bindings.getMapper(targetType);
    if (mapper == null) {
      throw new InvalidTypeException("Invalid type: missing argument mapper for type " + targetType.getKey());
    }
    Map<Annotation, MappingInterceptor> interceptors = getInterceptors(bindings, annotations);
    return new MappingProcessor(targetType, mapper, interceptors);
  }

  private static Map<Annotation, MappingInterceptor> getInterceptors(CommandBindings bindings, Set<Annotation> annotations) {
    Map<Annotation, MappingInterceptor<?>> interceptors = new LinkedHashMap<>();
    for (Annotation annotation : annotations) {
      MappingInterceptor<?> interceptor = bindings.getMappingInterceptor(annotation.annotationType());
      if (interceptor != null) {
        interceptors.put(annotation, interceptor);
      }
    }
    return Map.copyOf(interceptors);
  }

  public Object process(@Nonnull Arguments arguments, @Nonnull ContextMap commandContext) throws Throwable {
    MappingProcess process = new MappingProcess(type, arguments);
    process.setArgumentToMap(() -> mapper.prepare(arguments));
    interceptBefore(arguments, process, commandContext);
    if (process.isErroneous()) {
      throw process.getError();
    }
    if (!process.isSet()) {
      map(process, commandContext);
    }
    interceptAfter(arguments, process, commandContext);
    return finishProcess(process, commandContext);
  }

  private Object finishProcess(MappingProcess process, ContextMap commandContext) throws Throwable {
    if (process.isErroneous()) {
      throw process.getError();
    } else if (!process.isSet()) {
      if (process.hasArgumentToMap()) {
        map(process, commandContext);
        return finishProcess(process, commandContext);
      } else {
        throw new UnsatisfiedValueException("Unsatisfied value; no value set for " + type
            + " during the mapping process");
      }
    } else {
      return process.getValue();
    }
  }

  private void map(MappingProcess process, ContextMap context) {
    try {
      Object value = mapper.map(process.consumeArgumentToMap(), context, process.getContext());
      process.setValue(value);
    } catch (Throwable throwable) {
      process.fail(throwable);
    }
  }

  @SuppressWarnings("unchecked")
  private void interceptBefore(Arguments arguments, MappingProcess process, ContextMap context) {
    interceptors.forEach((annotation, interceptor) -> {
      try {
        interceptor.preprocess(annotation, process, context);
      } catch (Throwable t) {
        process.fail(t);
      }
    });
  }

  @SuppressWarnings("unchecked")
  private void interceptAfter(Arguments arguments, MappingProcess process, ContextMap context) {
    interceptors.forEach((annotation, interceptor) -> {
      try {
        interceptor.postprocess(annotation, process, context);
      } catch (Throwable t) {
        process.fail(t);
      }
    });
  }
}
