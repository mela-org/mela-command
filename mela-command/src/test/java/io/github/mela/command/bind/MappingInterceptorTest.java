package io.github.mela.command.bind;

import io.github.mela.command.bind.map.MappingInterceptor;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class MappingInterceptorTest {

  private static final int ANNOTATION_VALUE = 35;

  private TestInterceptor interceptor;
  private Dispatcher dispatcher;

  @BeforeEach
  void setUp() {
    interceptor = new TestInterceptor();
    CommandBindings bindings = CommandBindingsBuilder.create()
        .bindMapper(int.class, (a, c) -> 0)
        .bindMappingInterceptor(TestAnnotation.class, interceptor)
        .build();
    CommandGroup group = GroupBuilder.create()
        .withCommand(new TestCommand())
        .compile(new MethodHandleCompiler(bindings));
    dispatcher = new DefaultDispatcher(group);
  }

  @Test
  void testInterceptorExecution() {
    dispatcher.dispatch("foo", CommandContext.create());
    assertEquals(ANNOTATION_VALUE, interceptor.preprocessedValue,
        "preprocess method was incorrectly called or not called at all");
    assertEquals(ANNOTATION_VALUE, interceptor.postProcessedValue,
        "postprocess method was incorrectly called or not called at all");
  }

  public static final class TestCommand {

    @Command(labels = "foo")
    public void execute(@TestAnnotation(ANNOTATION_VALUE) int i) {

    }
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.TYPE_USE, ElementType.PARAMETER})
  private @interface TestAnnotation {
    int value();
  }

  private static class TestInterceptor implements MappingInterceptor<TestAnnotation> {

    int preprocessedValue, postProcessedValue;

    @Override
    public void preprocess(
        @Nonnull TestAnnotation annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
      preprocessedValue = annotation.value();
    }

    @Override
    public void postprocess(
        @Nonnull TestAnnotation annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
        postProcessedValue = annotation.value();
    }
  }

}
