package io.github.mela.command.bind;

import com.google.common.reflect.TypeToken;
import io.github.mela.command.bind.parameter.ParameterMarker;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class TargetType {

  private final AnnotatedType annotatedType;
  private final TypeKey<?> key;

  private TargetType(AnnotatedType annotatedType, TypeKey<?> key) {
    this.annotatedType = annotatedType;
    this.key = key;
  }

  public static TargetType create(AnnotatedType type) {
    return new TargetType(type, getKey(type));
  }

  @SuppressWarnings("UnstableApiUsage")
  private static TypeKey<?> getKey(AnnotatedType type) {
    return TypeKey.get(TypeToken.of(type.getType()), Arrays.stream(type.getAnnotations())
        .map(Annotation::annotationType)
        .filter((annotation) -> annotation.isAnnotationPresent(ParameterMarker.class))
        .findFirst()
        .orElse(null));
  }

  @SuppressWarnings("UnstableApiUsage")
  public TypeToken<?> getTypeToken() {
    return key.getTypeToken();
  }

  public TypeKey<?> getTypeKey() {
    return key;
  }

  public Type getType() {
    return annotatedType.getType();
  }

  public AnnotatedType getAnnotatedType() {
    return annotatedType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TargetType that = (TargetType) o;
    return Objects.equals(annotatedType, that.annotatedType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(annotatedType);
  }

  @Override
  public String toString() {
    return toString(annotatedType);
  }

  private String toString(AnnotatedType type) {
    String annotationsString = type.getAnnotations().length > 0
        ? Arrays.stream(type.getAnnotations()).map(Object::toString).collect(Collectors.joining(" ", "", " "))
        : "";
    String typeString = type instanceof AnnotatedParameterizedType
        ? toString((AnnotatedParameterizedType) type)
        : type.getType().toString();
    return annotationsString + typeString;
  }

  private String toString(AnnotatedParameterizedType type) {
    return getRaw(type.getType()) + "<"
        + Arrays.stream(type.getAnnotatedActualTypeArguments()).map(this::toString)
        .collect(Collectors.joining(", ")) + ">";
  }

  @SuppressWarnings("UnstableApiUsage")
  private Class<?> getRaw(Type type) {
    return TypeToken.of(type).getRawType();
  }
}
