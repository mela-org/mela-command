package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.parameter.ArrayParameter;
import com.github.stupremee.mela.command.bind.parameter.CollectionParameter;
import com.github.stupremee.mela.command.bind.parameter.CommandParameter;
import com.github.stupremee.mela.command.bind.process.ArgumentChain;
import com.github.stupremee.mela.command.parse.Arguments;
import com.google.common.base.Preconditions;
import com.google.inject.BindingAnnotation;
import com.google.inject.Key;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 11.09.2019 REFACTOR, proper exceptions
public final class Parameters {

  private final List<CommandParameter> parameters;

  private Parameters(List<CommandParameter> parameters) {
    this.parameters = List.copyOf(parameters);
  }

  @Nonnull
  public Object[] map(@Nonnull Arguments arguments, @Nonnull CommandContext context) throws Throwable {
    List<Object> mappedArgs = new ArrayList<>();
    ArgumentChain chain = new ArgumentChain(arguments);
    for (CommandParameter parameter : parameters) {
      mappedArgs.add(parameter.advance(chain, context));
    }

    if (parameters.size() != mappedArgs.size()) {
      throw new RuntimeException("Invalid amount of arguments; expected: "
          + parameters.size() + ", got: " + mappedArgs.size()); // TODO: 11.09.2019
    }
    return mappedArgs.toArray();
  }

  @Nonnull
  public static Parameters from(@Nonnull Method method, @Nonnull CommandBindings bindings) {
    checkNotNull(method);
    checkNotNull(bindings);
    if (method.getTypeParameters().length > 0) {
      throw new RuntimeException("Illegal method declaration: command methods must not have generic type parameters " +
          "(method: " + method + ")");
    }

    List<CommandParameter> commandParameters = new ArrayList<>();
    for (Parameter parameter : method.getParameters()) {
      Type type = parameter.getParameterizedType();
      Annotation[] annotations = parameter.getAnnotations();
      Map<Annotation, MappingInterceptor> interceptors = getInterceptors(annotations, bindings);
      ArgumentMapper<?> mapper = bindings.getMapper(getKey(type, annotations));
      if (mapper == null) {
        if (GenericReflection.isArray(type)) {
          Type componentType = GenericReflection.getComponentType(type);
          mapper = bindings.getMapper(getKey(componentType, annotations));
          Class<?> rawComponentType = GenericReflection.getRaw(componentType)
              .orElseThrow(() -> new RuntimeException("Invalid array component type at parameter "
                  + parameter + " in method " + method));
          commandParameters.add(new ArrayParameter(parameter, interceptors, mapper, rawComponentType));
        } else if (GenericReflection.isAssignableFromList(type)) {
          Type actualType = type instanceof ParameterizedType
              ? GenericReflection.getFirstActualTypeParameter((ParameterizedType) type)
              : String.class;
          mapper = bindings.getMapper(getKey(actualType, annotations));
          commandParameters.add(new CollectionParameter(parameter, interceptors, mapper));
        }

        if (mapper == null) {
          throw new RuntimeException("Missing parameter binding: " + parameter + " in method " + method);
        }
      } else {
        commandParameters.add(new CommandParameter(parameter, interceptors, mapper));
      }
    }
    return new Parameters(commandParameters);
  }

  private static Key<?> getKey(Type type, Annotation[] annotations) {
    return Arrays.stream(annotations)
        .map(Annotation::annotationType)
        .filter((annotation) -> annotation.isAnnotationPresent(BindingAnnotation.class))
        .findFirst()
        .map((annotation) -> (Key) Key.get(type, annotation))
        .orElseGet(() -> Key.get(type));
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

}
