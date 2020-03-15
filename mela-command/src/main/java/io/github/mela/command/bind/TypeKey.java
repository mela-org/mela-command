package io.github.mela.command.bind;

import com.google.common.reflect.TypeToken;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("UnstableApiUsage")
public final class TypeKey<T> {

  private final TypeToken<T> type;
  private final Class<? extends Annotation> annotationType;

  private TypeKey(TypeToken<T> type, Class<? extends Annotation> annotationType) {
    this.type = checkNotNull(type);
    this.annotationType = annotationType;
  }

  @Nonnull
  public static <T> TypeKey<T> get(@Nonnull TypeToken<T> typeToken) {
    return get(typeToken, null);
  }

  @Nonnull
  public static <T> TypeKey<T> get(
      @Nonnull TypeToken<T> typeToken, @Nullable Class<? extends Annotation> annotationType) {
    return new TypeKey<>(typeToken, annotationType);
  }

  @Nonnull
  public TypeToken<T> getType() {
    return type;
  }

  @Nullable
  public Class<? extends Annotation> getAnnotationType() {
    return annotationType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    TypeKey<?> that = (TypeKey<?>) o;
    return Objects.equals(type, that.type) &&
        Objects.equals(annotationType, that.annotationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, annotationType);
  }

  @Override
  public String toString() {
    return (annotationType != null ? "@" + annotationType : "") + " " + type;
  }
}
