package com.github.stupremee.mela.command.binding;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class EmptyGroup implements CommandTree.Group {

  public static final EmptyGroup INSTANCE = new EmptyGroup();

  private EmptyGroup() {
  }

  @Override
  public CommandTree.Group getParent() {
    return null;
  }

  @Override
  public String primaryAlias() {
    return null;
  }

  @Nonnull
  @Override
  public Set<CommandTree.Group> getChildren() {
    return Collections.emptySet();
  }

  @Nonnull
  @Override
  public Set<String> getAliases() {
    return Collections.emptySet();
  }

  @Nonnull
  @Override
  public ParameterBindings getParameterBindings() {
    return EmptyBindings.PARAMETER_BINDINGS;
  }

  @Nonnull
  @Override
  public InterceptorBindings getInterceptorBindings() {
    return EmptyBindings.INTERCEPTOR_BINDINGS;
  }

  @Nonnull
  @Override
  public ExceptionBindings getExceptionBindings() {
    return EmptyBindings.EXCEPTION_BINDINGS;
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
