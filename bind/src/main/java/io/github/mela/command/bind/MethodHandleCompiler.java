package io.github.mela.command.bind;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.mela.command.compile.CommandCompilerException;
import java.lang.reflect.Method;
import javax.annotation.Nonnull;


/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public final class MethodHandleCompiler extends BindingCompiler {

  @Inject
  MethodHandleCompiler(CommandBindings bindings) {
    super(bindings);
  }

  @Nonnull
  public static MethodHandleCompiler withBindings(@Nonnull CommandBindings bindings) {
    return new MethodHandleCompiler(checkNotNull(bindings));
  }

  @Override
  protected BindingCallable compile(@Nonnull Object command, @Nonnull Method method) {
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
