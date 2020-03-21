package io.github.mela.command.bind;

import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.DefaultDispatcher;
import io.github.mela.command.core.CommandDispatcher;
import io.github.mela.command.core.ImmutableGroup;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class BindingTest<T> {

  private final Supplier<T> factory;

  protected T command;
  protected CommandDispatcher dispatcher;

  protected BindingTest(Supplier<T> factory) {
    this.factory = factory;
  }

  protected abstract CommandBindingsBuilder configure(CommandBindingsBuilder builder);

  @BeforeEach
  void setUp() {
    command = factory.get();
    CommandBindings bindings = configure(CommandBindings.builder()).build();
    CommandGroup group = ImmutableGroup.builder()
        .add(command)
        .compile(new MethodHandleCompiler(bindings));
    dispatcher = new DefaultDispatcher(group);
  }

}
