package io.github.mela.command.bind.parameter;

import io.github.mela.command.bind.TargetType;

import javax.annotation.Nonnull;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandParameter {

  private final Parameter parameter;

  private final TargetType type;
  private final String name;
  private final String description;

  CommandParameter(Parameter parameter) {
    this.parameter = parameter;
    this.type = TargetType.create(parameter.getAnnotatedType());
    this.name = parameter.getName();
    this.description = parameter.isAnnotationPresent(Description.class)
        ? parameter.getAnnotation(Description.class).value()
        : "N/A";
  }

  @Nonnull
  public String getName() {
    return name;
  }

  @Nonnull
  public TargetType getType() {
    return type;
  }

  @Nonnull
  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CommandParameter that = (CommandParameter) o;
    return Objects.equals(parameter, that.parameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(parameter);
  }

  @Override
  public String toString() {
    return type.toString() + " " + name;
  }
}
