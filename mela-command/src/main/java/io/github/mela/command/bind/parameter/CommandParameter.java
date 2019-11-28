package io.github.mela.command.bind.parameter;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.ParameterKey;
import io.github.mela.command.bind.map.ArgumentChain;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.MappingInterceptor;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("rawtypes")
public class CommandParameter {

  private final Map<Annotation, MappingInterceptor> interceptors;
  private final ArgumentMapper<?> mapper;
  private final Type type;
  private final String name;
  private final String description;

  CommandParameter(Type type, String name, String description,
                   Map<Annotation, MappingInterceptor> interceptors, ArgumentMapper<?> mapper) {
    this.interceptors = Map.copyOf(interceptors);
    this.mapper = checkNotNull(mapper);
    this.type = type;
    this.name = name;
    this.description = description;
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
      throw new UnsatisfiedParameterException(this, "Unsatisfied parameter value; " + this + " was not set");
    } else {
      return process.getValue();
    }
  }

  private void mapNextArgument(MappingProcess process, CommandContext context) {
    try {
      process.setValue(mapper.map(process.getArgumentToMap(), context));
    } catch (Throwable throwable) {
      process.fail(throwable);
    }
  }

  @SuppressWarnings("unchecked")
  private void interceptBefore(MappingProcess process, CommandContext context) {
    interceptors.forEach((annotation, interceptor) -> {
      try {
        interceptor.preprocess(annotation, process, context);
      } catch (Throwable t) {
        process.fail(t);
      }
    });
  }

  @SuppressWarnings("unchecked")
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

  public String getDescription() {
    return description;
  }

  static CommandParameter from(Parameter parameter, CommandBindings bindings) {
    Type type = parameter.getParameterizedType();
    String name = parameter.getName();
    String description = parameter.isAnnotationPresent(Description.class)
        ? parameter.getAnnotation(Description.class).value()
        : "N/A";
    Annotation[] annotations = parameter.getAnnotations();
    Map<Annotation, MappingInterceptor> interceptors = getInterceptors(annotations, bindings);
    ArgumentMapper<?> mapper = bindings.getMapper(getKey(type, annotations));
    if (mapper == null) {
      if (GenericReflection.isArray(type)) {
        Type componentType = GenericReflection.getComponentType(type);
        mapper = bindings.getMapper(getKey(componentType, annotations));
        Class<?> rawComponentType = GenericReflection.getRaw(componentType);
        if (rawComponentType == null) {
          throw new InvalidParameterException("Invalid array component type at parameter " + parameter);
        }
        return new ArrayParameter(componentType, name, description, interceptors, mapper, rawComponentType);
      } else if (GenericReflection.isAssignable(type, List.class)) {
        Type actualType = type instanceof ParameterizedType
            ? ((ParameterizedType) type).getActualTypeArguments()[0]
            : String.class;
        mapper = bindings.getMapper(getKey(actualType, annotations));
        return new CollectionParameter(actualType, name, description, interceptors, mapper);
      }
      throw new InvalidParameterException("Missing parameter binding for " + parameter);
    } else {
      return new CommandParameter(type, name, description, interceptors, mapper);
    }
  }

  private static ParameterKey getKey(Type type, Annotation[] annotations) {
    return ParameterKey.get(type, Arrays.stream(annotations)
        .map(Annotation::annotationType)
        .filter((annotation) -> annotation.isAnnotationPresent(ParameterMarker.class))
        .findFirst()
        .orElse(null));
  }

  private static Map<Annotation, MappingInterceptor> getInterceptors(Annotation[] annotations, CommandBindings bindings) {
    Map<Annotation, MappingInterceptor<?>> interceptors = new LinkedHashMap<>();
    for (Annotation annotation : annotations) {
      MappingInterceptor<?> interceptor = bindings.getArgumentInterceptor(annotation.annotationType());
      if (interceptor != null) {
        interceptors.put(annotation, interceptor);
      }
    }
    return Map.copyOf(interceptors);
  }

  @Override
  public String toString() {
    return interceptors.keySet().stream().map(Objects::toString).collect(Collectors.joining(" "))
        + " " + type + " " + name;
  }
}
