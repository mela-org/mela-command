package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class TargetType {

  private final AnnotatedType annotatedType;
  private final TypeKey<?> key;

  private TargetType(AnnotatedType annotatedType) {
    this.annotatedType = checkNotNull(annotatedType);
    this.key = getKey(annotatedType);
  }

  @Nonnull
  public static TargetType of(@Nonnull AnnotatedType type) {
    return new TargetType(type);
  }

  @SuppressWarnings("UnstableApiUsage")
  private TypeKey<?> getKey(AnnotatedType type) {
    return TypeKey.get(TypeToken.of(type.getType()), Arrays.stream(type.getAnnotations())
        .map(Annotation::annotationType)
        .filter((annotation) -> annotation.isAnnotationPresent(TypeClassifier.class))
        .findFirst()
        .orElse(null));
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  public TypeToken<?> getTypeToken() {
    return key.getTypeToken();
  }

  @Nonnull
  public TypeKey<?> getTypeKey() {
    return key;
  }

  @Nonnull
  public Type getType() {
    return annotatedType.getType();
  }

  @Nonnull
  public AnnotatedType getAnnotatedType() {
    return annotatedType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
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
        ? Arrays.stream(type.getAnnotations())
          .map(Object::toString)
          .collect(Collectors.joining(" ", "", " "))
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
