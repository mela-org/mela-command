package io.github.mela.command.bind.guice;

import com.google.inject.Inject;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.MethodHandleCompiler;
import io.github.mela.command.compile.CommandCompiler;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class MethodHandleCompilerProvider extends SingletonProvider<CommandCompiler> {

  private final CommandBindings bindings;

  @Inject
  public MethodHandleCompilerProvider(CommandBindings bindings) {
    this.bindings = bindings;
  }

  @Override
  protected CommandCompiler create() {
    return MethodHandleCompiler.withBindings(bindings);
  }
}
