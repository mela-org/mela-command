package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.bind.EmptyBindings;
import com.github.stupremee.mela.command.bind.ExceptionBindings;
import com.github.stupremee.mela.command.bind.InterceptorBindings;
import com.github.stupremee.mela.command.bind.ParameterBindings;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class EmptyGroup implements Group {

  static final EmptyGroup INSTANCE = new EmptyGroup();

  private EmptyGroup() {
  }

  @Override
  public Group getParent() {
    return null;
  }

  @Override
  public String getPrimaryAlias() {
    return null;
  }

  @Nonnull
  @Override
  public Set<Group> getChildren() {
    return Collections.emptySet();
  }

  @Nonnull
  @Override
  public Set<String> getNames() {
    return Collections.emptySet();
  }

  @Nonnull
  @Override
  public ParameterBindings getParameterBindings() {
    return EmptyBindings.INSTANCE;
  }

  @Nonnull
  @Override
  public InterceptorBindings getInterceptorBindings() {
    return EmptyBindings.INSTANCE;
  }

  @Nonnull
  @Override
  public ExceptionBindings getExceptionBindings() {
    return EmptyBindings.INSTANCE;
  }

  @Nonnull
  @Override
  public Collection<?> getCommandObjects() {
    return Collections.emptySet();
  }

  @Override
  public String toString() {
    return "[]";
  }
}
