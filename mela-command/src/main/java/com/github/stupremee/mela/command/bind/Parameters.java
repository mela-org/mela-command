package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.parameter.*;
import com.github.stupremee.mela.command.bind.process.MappingProcess;
import com.github.stupremee.mela.command.parse.Arguments;
import com.google.inject.BindingAnnotation;
import com.google.inject.Key;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 11.09.2019 REFACTOR, proper exceptions
public final class Parameters {

  private final List<CommandParameter> parameters;

  private Parameters(List<CommandParameter> parameters) {
    this.parameters = parameters;
  }

  public Object[] map(Arguments arguments, CommandContext context) throws Throwable {
    List<Object> mappedArgs = new ArrayList<>();
    for (
        int parameterIndex = 0, argumentIndex = 0;
        parameterIndex < parameters.size() && argumentIndex < arguments.size();
        parameterIndex++, argumentIndex++
    ) {
      CommandParameter parameter = parameters.get(parameterIndex);
      MappingProcess result = parameter.process(argumentIndex, arguments, context);
      if (result.isSuccessful()) {
        if (result.shouldBeSkipped()) {
          argumentIndex--;
        }
        mappedArgs.add(result.getValue());
      } else {
        throw result.getError();
      }
    }

    if (parameters.size() != mappedArgs.size()) {
      throw new RuntimeException("Invalid amount of arguments; expected: "
          + parameters.size() + ", got: " + mappedArgs.size()); // TODO: 11.09.2019
    }
    return mappedArgs.toArray();
  }

  public static Parameters from(Method method, CommandBindings bindings) {
    if (method.getTypeParameters().length > 0) {
      throw new RuntimeException("Illegal method declaration: command methods must not have generic type parameters " +
          "(method: " + method + ")");
    }

    List<CommandParameter> commandParameters = new ArrayList<>();
    Iterator<Parameter> parameters = Arrays.asList(method.getParameters()).iterator();
    while (parameters.hasNext()) {
      Parameter parameter = parameters.next();
      Type type = parameter.getParameterizedType();
      Annotation[] annotations = parameter.getAnnotations();
      Map<Annotation, ArgumentInterceptor> interceptors = getInterceptors(annotations, bindings);
      ArgumentMapper<?> mapper = bindings.getMapper(getKey(type, annotations));
      if (mapper == null) {
        if (isArray(type)) {
          if (parameters.hasNext()) {
            throw new RuntimeException("Unbound arrays must be the last parameter of a method (Method: "
                + method + "; Parameter: " + parameter + ")");
          } else {
            Class<?> componentType = ((Class) type).getComponentType();
            mapper = bindings.getMapper(getKey(componentType, annotations));
            commandParameters.add(new ArrayParameter(type, interceptors, mapper));
          }
        } else if (isCollection(type)) {
          if (parameters.hasNext()) {
            throw new RuntimeException("Unbound collections must be the last parameter of a method (Method: "
                + method + "; Parameter: " + parameter + ")");
          } else {
            Type actualType = getFirstActualTypeParameter(type).orElse(String.class);
            mapper = bindings.getMapper(getKey(actualType, annotations));
            commandParameters.add(new CollectionParameter(type, interceptors, mapper));
          }
        } else {
          throw new RuntimeException("Unbound parameter: " + parameter + " in method " + method);
        }
      } else if (parameter.isAnnotationPresent(Flag.class)) {
        Flag flagAnnotation = parameter.getAnnotation(Flag.class);
        commandParameters.add(new FlagParameter(type, interceptors, mapper, flagAnnotation.value()));
      } else if (parameter.isAnnotationPresent(Rest.class)) {
        if (parameters.hasNext()) {
          throw new RuntimeException("Parameters annotated with @JoinedRest must be the last parameter " +
              "of their method (Method: " + method + "; Parameter: " + parameter + ")");
        } else {
          commandParameters.add(new RestParameter(type, interceptors, mapper));
        }
      } else {
        commandParameters.add(new CommandParameter(type, interceptors, mapper));
      }
    }
    return new Parameters(commandParameters);
  }

  private static Optional<Type> getFirstActualTypeParameter(Type type) {
    return type instanceof ParameterizedType
        ? Optional.of(((ParameterizedType) type).getActualTypeArguments()[0])
        : Optional.empty();
  }

  private static boolean isCollection(Type type) {
    return (type instanceof ParameterizedType
            ? (Class<?>) ((ParameterizedType) type).getRawType()
            : (Class<?>) type)
        .isAssignableFrom(List.class);

  }

  private static boolean isArray(Type type) {
    return type instanceof Class && ((Class) type).isArray();
  }

  private static Key<?> getKey(Type type, Annotation[] annotations) {
    return Arrays.stream(annotations)
        .map(Annotation::annotationType)
        .filter((annotation) -> annotation.isAnnotationPresent(BindingAnnotation.class))
        .findFirst()
        .map((annotation) -> (Key) Key.get(type, annotation))
        .orElseGet(() -> Key.get(type));
  }

  private static Map<Annotation, ArgumentInterceptor> getInterceptors(Annotation[] annotations, CommandBindings bindings) {
    Map<Annotation, ArgumentInterceptor<?>> interceptors = new LinkedHashMap<>();
    for (Annotation annotation : annotations) {
      ArgumentInterceptor<?> interceptor = bindings.getArgumentInterceptor(annotation.annotationType());
      interceptors.put(annotation, interceptor);
    }
    return Map.copyOf(interceptors);
  }

}
