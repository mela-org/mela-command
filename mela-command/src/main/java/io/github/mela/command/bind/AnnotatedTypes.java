package io.github.mela.command.bind;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class AnnotatedTypes {

  private AnnotatedTypes() {}

  public static AnnotatedType fromType(Type type) {
    checkArgument(!(type instanceof TypeVariable), "Type must not be a type variable");
    checkArgument(!(type instanceof WildcardType), "Type must not be a wildcard type");
    if (type instanceof Class) {
      Class<?> clazz = (Class<?>) type;
      return clazz.isArray() ? new UnAnnotatedArrayType(clazz) : new UnAnnotatedType(clazz);
    } else if (type instanceof ParameterizedType) {
      return new UnAnnotatedParameterizedType((ParameterizedType) type);
    } else if (type instanceof GenericArrayType) {
      return new UnAnnotatedArrayType((GenericArrayType) type);
    }
    throw new AssertionError();
  }

  private static class UnAnnotatedType implements AnnotatedType {

    private static final Annotation[] EMPTY = {};

    private final Type type;

    protected UnAnnotatedType(Type type) {
      this.type = type;
    }

    @Override
    public Type getType() {
      return type;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
      return null;
    }

    @Override
    public Annotation[] getAnnotations() {
      return EMPTY;
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
      return EMPTY;
    }

    @Override
    public AnnotatedType getAnnotatedOwnerType() {
      return null;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      UnAnnotatedType that = (UnAnnotatedType) o;
      return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
      return Objects.hash(type);
    }
  }

  private static class UnAnnotatedParameterizedType extends UnAnnotatedType implements AnnotatedParameterizedType {

    private final AnnotatedType[] actualTypeArguments;

    protected UnAnnotatedParameterizedType(ParameterizedType type) {
      super(type);
      this.actualTypeArguments = Arrays.stream(type.getActualTypeArguments())
          .map(AnnotatedTypes::fromType)
          .toArray(AnnotatedType[]::new);
    }

    @Override
    public AnnotatedType[] getAnnotatedActualTypeArguments() {
      return actualTypeArguments;
    }
  }

  private static class UnAnnotatedArrayType extends UnAnnotatedType implements AnnotatedArrayType {

    private final AnnotatedType componentType;

    protected UnAnnotatedArrayType(GenericArrayType type) {
      super(type);
      this.componentType = fromType(type.getGenericComponentType());
    }

    protected UnAnnotatedArrayType(Class<?> type) {
      super(type);
      this.componentType = fromType(type.getComponentType());
    }

    @Override
    public AnnotatedType getAnnotatedGenericComponentType() {
      return componentType;
    }
  }

}
