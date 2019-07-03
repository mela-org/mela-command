package com.github.stupremee.mela.command;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.function.Supplier;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class CommandTest<T> {

  @Inject
  private T subject;
  @Inject
  private CommandCallable callable;

  private final Supplier<? extends Module> moduleFactory;

  protected CommandTest(Supplier<? extends Module> moduleFactory) {
    this.moduleFactory = moduleFactory;
  }

  @BeforeEach
  public void setUpAndInject() {
    Module module = moduleFactory.get();
    Injector injector = Guice.createInjector(module);
    injector.injectMembers(this);
  }

  @AfterEach
  public void tearDown() {
    this.subject = null;
    this.callable = null;
  }

  protected final T getSubject() {
    return subject;
  }

  protected final boolean callCommand(String command) {
    return callable.selectChild(command).callWithRemainingArgs(new CommandContext());
  }
}
