package io.github.mela.command.bind.parameter;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class GenericReflection {

  private GenericReflection() {}

  public static Optional<Class<?>> getRaw(Type type) {
    if (type instanceof Class<?>) {
      return Optional.of((Class<?>) type);
    } else if (type instanceof ParameterizedType) {
      return getRaw(((ParameterizedType) type).getRawType());
    } else {
      return Optional.empty();
    }
  }

  public static Type getComponentType(Type array) {
    if (array instanceof GenericArrayType) {
      return ((GenericArrayType) array).getGenericComponentType();
    } else if (array instanceof Class<?> && ((Class<?>) array).isArray()) {
      return ((Class<?>) array).getComponentType();
    } else {
      throw new IllegalArgumentException("Argument must be an array type");
    }
  }

  public static boolean isAssignable(Type type, Class from) {
    if (type instanceof ParameterizedType) {
      return ((Class<?>) ((ParameterizedType) type).getRawType()).isAssignableFrom(from);
    } else if (type instanceof Class<?>) {
      return ((Class<?>) type).isAssignableFrom(from);
    } else {
      return false;
    }
  }

  public static boolean isArray(Type type) {
    return (type instanceof Class && ((Class) type).isArray())
        || type instanceof GenericArrayType;
  }

}
