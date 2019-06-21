package com.github.stupremee.mela.command.binder;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CommandBinderTest {

  private CommandBindings bindings;
  private BinderTestModule module;

  @BeforeEach
  public void setUp() {
    this.module = new BinderTestModule();
    Injector injector = Guice.createInjector(module);
    this.bindings = injector.getInstance(CommandBindings.class);
  }

  @AfterEach
  public void tearDown() {
    this.bindings = null;
    this.module = null;
  }

  @Test
  public void testInterceptorBinding() {
    assertEquals(BinderTestModule.INTERCEPTOR, bindings.getInterceptor(TestAnnotation.class),
        "Interceptor was bound incorrectly");
  }

  @Test
  public void testExceptionBinding() {
    assertEquals(BinderTestModule.EXCEPTION_HANDLER, bindings.getHandler(TestException.class),
        "Exception handler was bound incorrectly");
  }

  @Test
  public void testIgnoringInheritanceExceptionBinding() {
    assertNull(bindings.getHandler(RuntimeException.class),
        "Binder did not ignore inheritance for Exception handler");
  }

  @Test
  public void testParameterBinding() {
    // TODO: 21.06.2019
  }

  @Test
  public void testAnnotatedParameterBinding() {
    // TODO: 21.06.2019
  }

  @Test
  public void testOutOfScopeConfiguration() {
    assertThrows(IllegalStateException.class, module::configure,
        "Configuring module out of scope did not throw IllegalStateException");
  }
}
