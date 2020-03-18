package io.github.mela.command.bind.map;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.parameter.InvalidTypeError;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
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
    return create(bindings, TargetType.create(parameter.getAnnotatedType()),
        new LinkedHashSet<>(Arrays.asList(parameter.getAnnotations())));
  }

  @Nonnull
  public static MappingProcessor fromTargetType(@Nonnull CommandBindings bindings, @Nonnull TargetType type) {
    return create(bindings, type, new LinkedHashSet<>());
  }

  private static MappingProcessor create(CommandBindings bindings, TargetType type, Set<Annotation> annotations) {
    annotations.addAll(Arrays.asList(type.getAnnotatedType().getAnnotations()));
    ArgumentMapper mapper = bindings.getMapper(type);
    if (mapper == null) {
      throw new InvalidTypeError("Invalid type: missing argument mapper for type " + type.getTypeKey());
    }
    Map<Annotation, MappingInterceptor> interceptors = getInterceptors(bindings, annotations);
    return new MappingProcessor(type, mapper, interceptors);
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
    checkNotNull(commandContext);
    MappingProcess process = MappingProcess.create(type, arguments);
    intercept(process, commandContext, true);
    Arguments lastMappingArgs = process.getArgumentsToMap();
    if (!process.isErroneous() && !process.isSet()) {
      map(commandContext, process);
    }
    intercept(process, commandContext, false);
    return finishProcess(lastMappingArgs, commandContext, process);
  }

  private Object finishProcess(Arguments lastMappingArgs, ContextMap commandContext, MappingProcess process) throws Throwable {
    if (process.isErroneous()) {
      throw process.getError();
    } else if (!process.isSet()) {
      if (process.getArgumentsToMap() != lastMappingArgs) {
        map(commandContext, process);
        return finishProcess(process.getArguments(), commandContext, process);
      } else {
        throw new UnsatisfiedValueException("Unsatisfied value; no value set for " + type
            + " during the mapping process");
      }
    } else {
      return process.getValue();
    }
  }

  private void map(ContextMap commandContext, MappingProcess process) {
    try {
      Object value = mapper.map(process.getArgumentsToMap(), commandContext);
      process.setValue(value);
    } catch (Throwable throwable) {
      process.fail(throwable);
    }
  }

  @SuppressWarnings("unchecked")
  private void intercept(MappingProcess process, ContextMap context, boolean before) {
    for (Map.Entry<Annotation, MappingInterceptor> entry : interceptors.entrySet()) {
      Annotation annotation = entry.getKey();
      MappingInterceptor interceptor = entry.getValue();
      if (before) {
        interceptor.preprocess(annotation, process, context);
      } else {
        interceptor.postprocess(annotation, process, context);
      }
    }
  }
}
