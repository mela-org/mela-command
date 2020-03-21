package io.github.mela.command.bind.parameter;

import static com.google.common.base.Preconditions.checkNotNull;


import io.github.mela.command.bind.TargetType;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Parameter;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.inject.Named;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandParameter implements AnnotatedElement {

  private final Parameter parameter;

  private final TargetType targetType;
  private final String name;
  private final String description;

  private CommandParameter(Parameter parameter) {
    this.parameter = parameter;
    this.targetType = TargetType.of(parameter.getAnnotatedType());
    this.name = parameter.isAnnotationPresent(Named.class)
        ? parameter.getAnnotation(Named.class).value()
        : parameter.getName();
    this.description = parameter.isAnnotationPresent(Description.class)
        ? parameter.getAnnotation(Description.class).value()
        : "N/A";
  }

  public static CommandParameter of(@Nonnull Parameter parameter) {
    checkNotNull(parameter);
    return new CommandParameter(parameter);
  }

  @Nonnull
  public String getName() {
    return name;
  }

  @Nonnull
  public TargetType getTargetType() {
    return targetType;
  }

  @Nonnull
  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommandParameter that = (CommandParameter) o;
    return Objects.equals(parameter, that.parameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parameter);
  }

  @Override
  public String toString() {
    return targetType.toString() + " " + name;
  }

  @Override
  public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
    return parameter.getAnnotation(annotationClass);
  }

  @Override
  public Annotation[] getAnnotations() {
    return parameter.getAnnotations();
  }

  @Override
  public Annotation[] getDeclaredAnnotations() {
    return parameter.getDeclaredAnnotations();
  }
}
