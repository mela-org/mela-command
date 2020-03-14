package io.github.mela.command.bind;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class TypeKey {

  private final Type type;
  private final Class<? extends Annotation> annotationType;

  private TypeKey(Type type, Class<? extends Annotation> annotationType) {
    this.type = checkNotNull(type);
    this.annotationType = annotationType;
  }

  @Nonnull
  public static TypeKey get(@Nonnull Type type) {
    return get(type, null);
  }

  @Nonnull
  public static TypeKey get(@Nonnull Type type, @Nullable Class<? extends Annotation> annotationType) {
    return new TypeKey(type, annotationType);
  }

  @Nonnull
  public Type getType() {
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
    TypeKey that = (TypeKey) o;
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
