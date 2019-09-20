package io.github.mela.command;

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
public abstract class SingleSubjectTest<T> {

  @Inject
  private T subject;

  private final Supplier<? extends Module> moduleFactory;

  protected SingleSubjectTest(Supplier<? extends Module> moduleFactory) {
    this.moduleFactory = moduleFactory;
  }

  @BeforeEach
  public void setUpAndInject() {
    Module module = moduleFactory.get();
    Injector injector = Guice.createInjector(module);
    injector.injectMembers(this);
  }

  @AfterEach
  public void nullify() {
    this.subject = null;
  }

  protected final T getSubject() {
    return subject;
  }
}
