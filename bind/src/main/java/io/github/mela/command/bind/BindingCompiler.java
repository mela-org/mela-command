package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.collect.ImmutableSet;
import io.github.mela.command.compile.CommandCompiler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class BindingCompiler implements CommandCompiler {

  protected final CommandBindings bindings;

  protected BindingCompiler(@Nonnull CommandBindings bindings) {
    this.bindings = checkNotNull(bindings);
  }

  @Nonnull
  @Override
  public final Set<? extends BindingCallable> compile(@Nonnull Object command) {
    return Arrays.stream(command.getClass().getMethods())
        .filter((method) -> method.isAnnotationPresent(Command.class))
        .map((method) -> compile(command, method))
        .collect(ImmutableSet.toImmutableSet());
  }

  protected abstract BindingCallable compile(@Nonnull Object object, @Nonnull Method method);
}
