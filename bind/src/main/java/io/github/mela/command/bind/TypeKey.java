package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("UnstableApiUsage")
public final class TypeKey<T> {

  private final TypeToken<T> typeToken;
  private final Class<? extends Annotation> annotationType;

  private TypeKey(TypeToken<T> typeToken, Class<? extends Annotation> annotationType) {
    this.typeToken = checkNotNull(typeToken);
    this.annotationType = annotationType;
  }

  @Nonnull
  public static <T> TypeKey<T> get(@Nonnull Class<T> type) {
    return get(type, null);
  }

  @Nonnull
  public static <T> TypeKey<T> get(
      @Nonnull Class<T> type, @Nullable Class<? extends Annotation> annotationType) {
    return get(TypeToken.of(type), annotationType);
  }

  @Nonnull
  public static <T> TypeKey<T> get(@Nonnull TypeToken<T> typeToken) {
    return get(typeToken, null);
  }

  @Nonnull
  public static <T> TypeKey<T> get(
      @Nonnull TypeToken<T> typeToken, @Nullable Class<? extends Annotation> annotationType) {
    if (annotationType != null) {
      checkArgument(annotationType.isAnnotationPresent(TypeClassifier.class),
          "Annotation " + annotationType + " does not have the @TypeClassifier annotation");
    }
    return new TypeKey<>(typeToken, annotationType);
  }

  @Nonnull
  public TypeToken<T> getTypeToken() {
    return typeToken;
  }

  @Nullable
  public Class<? extends Annotation> getAnnotationType() {
    return annotationType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypeKey<?> that = (TypeKey<?>) o;
    return Objects.equals(typeToken, that.typeToken)
        && Objects.equals(annotationType, that.annotationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(typeToken, annotationType);
  }

  @Override
  public String toString() {
    return (annotationType != null ? "@" + annotationType : "") + " " + typeToken;
  }
}
