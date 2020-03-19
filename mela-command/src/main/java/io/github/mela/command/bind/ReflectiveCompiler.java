package io.github.mela.command.bind;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.mela.command.compile.CommandCompiler;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public final class ReflectiveCompiler implements CommandCompiler {

  private final CommandBindings bindings;

  @Inject
  public ReflectiveCompiler(@Nonnull CommandBindings bindings) {
    this.bindings = checkNotNull(bindings);
  }

  @Nonnull
  @Override
  public Set<ReflectiveCallable> compile(@Nonnull Object command) {
    return Arrays.stream(command.getClass().getMethods())
        .filter((method) -> method.isAnnotationPresent(Command.class))
        .map((method) -> compile(command, method))
        .collect(Collectors.toUnmodifiableSet());
  }

  private ReflectiveCallable compile(Object command, Method method) {
    try {
      return ReflectiveCallable.from(command, method, bindings);
    } catch (NoSuchMethodException e) {
      throw new AssertionError("A method directly taken from a Class cannot be found anymore. Huh?", e);
    } catch (IllegalAccessException e) {
      throw new InvalidCommandMethodException("Command method " + method
          + " cannot be accessed. Check whether it is public", e);
    }
  }
}
