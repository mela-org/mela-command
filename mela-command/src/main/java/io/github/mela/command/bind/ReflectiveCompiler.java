package io.github.mela.command.bind;

import com.google.inject.Inject;
import io.github.mela.command.compile.CommandCompiler;
import io.github.mela.command.core.CommandCallable;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ReflectiveCompiler implements CommandCompiler {

  private final CommandBindings bindings;

  @Inject
  public ReflectiveCompiler(CommandBindings bindings) {
    this.bindings = bindings;
  }

  @Nonnull
  @Override
  public Set<? extends CommandCallable> compile(@Nonnull Object command) {
    return Arrays.stream(command.getClass().getMethods())
        .filter((method) -> method.isAnnotationPresent(Command.class))
        .map((method) -> compile(command, method))
        .collect(Collectors.toUnmodifiableSet());
  }

  private CommandCallable compile(Object command, Method method) {
    try {
      return ReflectiveCallable.from(command, method, bindings);
    } catch (NoSuchMethodException e) {
      throw new AssertionError("A method directly taken from a Class cannot be found anymore. Huh?", e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Command methods must be public", e);
    }
  }
}
