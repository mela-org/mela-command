package com.github.stupremee.mela.command.bind.tree;

import com.github.stupremee.mela.command.bind.ExceptionBindings;
import com.github.stupremee.mela.command.bind.InterceptorBindings;
import com.github.stupremee.mela.command.bind.ParameterBindings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public interface Group {

  @Nullable
  Group getParent();

  @Nonnull
  Set<Group> getChildren();

  @Nonnull
  Set<String> getNames();

  @Nonnull
  ParameterBindings getParameterBindings();

  @Nonnull
  InterceptorBindings getInterceptorBindings();

  @Nonnull
  ExceptionBindings getExceptionBindings();

  @Nonnull
  Set<Object> getCommandObjects();

}
