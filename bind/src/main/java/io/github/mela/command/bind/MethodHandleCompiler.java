package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.mela.command.compile.CommandCompiler;
import io.github.mela.command.compile.CommandCompilerException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import javax.annotation.Nonnull;


/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public final class MethodHandleCompiler implements CommandCompiler {

  private final CommandBindings bindings;

  @Inject
  MethodHandleCompiler(CommandBindings bindings) {
    this.bindings = bindings;
  }

  @Nonnull
  public static MethodHandleCompiler withBindings(@Nonnull CommandBindings bindings) {
    return new MethodHandleCompiler(checkNotNull(bindings));
  }

  @Nonnull
  @Override
  public Set<MethodHandleCallable> compile(@Nonnull Object command) {
    return Arrays.stream(command.getClass().getMethods())
        .filter((method) -> method.isAnnotationPresent(Command.class))
        .map((method) -> compile(command, method))
        .collect(ImmutableSet.toImmutableSet());
  }

  private MethodHandleCallable compile(Object command, Method method) {
    try {
      return MethodHandleCallable.from(command, method, bindings);
    } catch (NoSuchMethodException e) {
      throw new AssertionError(
          "A method directly taken from a Class cannot be found anymore. Huh?", e);
    } catch (IllegalAccessException e) {
      throw new CommandCompilerException("Command method " + method
          + " cannot be accessed. Check whether it and its enclosing class are public.", e);
    }
  }
}
