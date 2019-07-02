package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.handle.ExceptionHandler;
import com.github.stupremee.mela.command.intercept.Interceptor;
import com.github.stupremee.mela.command.map.ArgumentMapper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class BindingTest {

  @Inject
  private CommandTree tree;

  @BeforeEach
  public void setUp() {
    Injector injector = Guice.createInjector(new SimpleModule(), new OverrideModule());
    injector.injectMembers(this);
    tree.stepToRoot();
  }

  @Test
  public void testCommandPresence() {
    Collection<?> commandObjects = tree.getCurrent().getCommandObjects();
    assertTrue(commandObjects.contains(SimpleModule.COMMAND),
        "Root does not contain command object from SimpleModule");
    assertTrue(commandObjects.contains(OverrideModule.ADDITIONAL_COMMAND),
        "Root does not contain command object from OverrideModule");
  }

  @Test
  public void testParameterBindingPresence() {
    Optional<ArgumentMapper<Object>> binding = tree.getCurrent()
        .getParameterBindings().getMapper(Key.get(Object.class));
    assertTrue(binding.isPresent(), "Parameter bind from SimpleModule is not present");
    assertEquals(SimpleModule.MAPPER, binding.get(), "Parameter bind dies not equal set bind");
  }

  @Test
  public void testInterceptorBindingPresence() {
    Optional<Interceptor<TestAnnotation>> binding = tree.getCurrent()
        .getInterceptorBindings().getInterceptor(TestAnnotation.class);
    assertTrue(binding.isPresent(), "Interceptor bind from SimpleModule is not present");
    assertEquals(SimpleModule.INTERCEPTOR, binding.get(), "Interceptor bind does not equal set bind");
  }

  @Test
  public void testHandlerBindingPresence() {
    Optional<ExceptionHandler<TestException>> binding = tree.getCurrent()
        .getExceptionBindings().getHandler(TestException.class);
    assertTrue(binding.isPresent(), "Exception bind from SimpleModule is not present");
    assertEquals(SimpleModule.HANDLER, binding.get(), "Exception bind does not equal set bind");
  }

  @Test
  public void testMapperOverridePresence() {
    Optional<ArgumentMapper<Object>> binding = tree.getCurrent()
        .getParameterBindings().getMapper(Key.get(Object.class));
    assertTrue(binding.isPresent(), "Parameter bind from OverrideModule is not present");
    assertEquals(OverrideModule.MAPPER_OVERRIDE, binding.get(),
        "Parameter bind does not equal set override bind");
  }

  @Test
  public void testInterceptorOverridePresence() {
    Optional<Interceptor<TestAnnotation>> binding = tree.getCurrent()
        .getInterceptorBindings().getInterceptor(TestAnnotation.class);
    assertTrue(binding.isPresent(), "Interceptor bind from OverrideModule is not present");
    assertEquals(OverrideModule.INTERCEPTOR_OVERRIDE, binding.get(),
        "Interceptor bind does not equal set override bind");
  }

  @Test
  public void testHandlerOverridePresence() {
    Optional<ExceptionHandler<TestException>> binding = tree.getCurrent()
        .getExceptionBindings().getHandler(TestException.class);
    assertTrue(binding.isPresent(), "Exception bind from OverrideModule is not present");
    assertEquals(OverrideModule.HANDLER_OVERRIDE, binding.get(),
        "Exception bind does not equal set override bind");
  }

}
