package io.github.mela.command.bind.map;

import io.github.mela.command.core.Arguments;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.parameter.InvalidTypeException;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Parameter;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("rawtypes")
public class MappingProcessor {

  private final TargetType type;
  private final ArgumentMapper<?> mapper;
  private final Map<Annotation, MappingInterceptor> interceptors;

  private MappingProcessor(TargetType type, ArgumentMapper<?> mapper, Map<Annotation, MappingInterceptor> interceptors) {
    this.type = type;
    this.mapper = mapper;
    this.interceptors = interceptors;
  }

  @Nonnull
  public static MappingProcessor fromParameter(@Nonnull CommandBindings bindings, @Nonnull Parameter parameter) {
    return create(bindings, parameter.getAnnotatedType(), new LinkedHashSet<>(Arrays.asList(parameter.getAnnotations())));
  }

  @Nonnull
  public static MappingProcessor fromAnnotatedType(@Nonnull CommandBindings bindings, @Nonnull AnnotatedType type) {
    return create(bindings, type, new LinkedHashSet<>());
  }

  private static MappingProcessor create(CommandBindings bindings, AnnotatedType type, Set<Annotation> annotations) {
    TargetType targetType = TargetType.create(type);
    annotations.addAll(Arrays.asList(type.getAnnotations()));
    ArgumentMapper mapper = bindings.getMapper(targetType);
    if (mapper == null) {
      throw new InvalidTypeException("Invalid type: missing argument mapper for type " + targetType.getTypeKey());
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

  @Nullable
  public Object process(@Nonnull Arguments arguments, @Nonnull ContextMap commandContext) throws Throwable {
    MappingProcess process = MappingProcess.create(type);
    checkNotNull(commandContext);
    interceptBefore(arguments, process, commandContext);
    if (!process.isErroneous() && !process.isSet()) {
      map(arguments, commandContext, process);
    }
    interceptAfter(arguments, process, commandContext);
    return finishProcess(arguments, commandContext, process);
  }

  private Object finishProcess(Arguments arguments, ContextMap commandContext, MappingProcess process) throws Throwable {
    if (process.isErroneous()) {
      throw process.getError();
    } else if (!process.isSet()) {
      if (process.hasArgumentToMap()) {
        map(arguments, commandContext, process);
        return finishProcess(arguments, commandContext, process);
      } else {
        throw new UnsatisfiedValueException("Unsatisfied value; no value set for " + type
            + " during the mapping process");
      }
    } else {
      return process.getValue();
    }
  }

  private void map(Arguments arguments, ContextMap commandContext, MappingProcess process) {
    try {
      Object value = mapper.map(arguments, commandContext, process.getContext());
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
