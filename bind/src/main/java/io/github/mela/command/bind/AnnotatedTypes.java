package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class AnnotatedTypes {

  public static final AnnotatedType STRING = fromType(String.class);

  private AnnotatedTypes() {
  }

  @Nonnull
  public static AnnotatedType fromType(@Nonnull Type type) {
    checkNotNull(type);
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

  @Nonnull
  public static AnnotatedType[] getActualTypeArguments(@Nonnull AnnotatedType type) {
    checkNotNull(type);
    return type instanceof AnnotatedParameterizedType
        ? ((AnnotatedParameterizedType) type).getAnnotatedActualTypeArguments()
        : new AnnotatedType[0];
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

  }

  private static class UnAnnotatedParameterizedType
      extends UnAnnotatedType implements AnnotatedParameterizedType {

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

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      if (!super.equals(o)) {
        return false;
      }
      UnAnnotatedParameterizedType that = (UnAnnotatedParameterizedType) o;
      return Arrays.equals(actualTypeArguments, that.actualTypeArguments);
    }

    @Override
    public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + Arrays.hashCode(actualTypeArguments);
      return result;
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

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      if (!super.equals(o)) {
        return false;
      }
      UnAnnotatedArrayType that = (UnAnnotatedArrayType) o;
      return Objects.equals(componentType, that.componentType);
    }

    @Override
    public int hashCode() {
      return Objects.hash(super.hashCode(), componentType);
    }
  }

}
